package com.dineease.service;

import com.dineease.db.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBAuthService implements AuthService {

    @Override
    public UserSession login(String username, String password) {
        String normalizedUsername = username == null ? "" : username.trim();
        String normalizedPassword = password == null ? "" : password.trim();

        if (normalizedUsername.isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (normalizedPassword.isEmpty()) {
            throw new IllegalArgumentException("Password is required.");
        }

        // Check if database has any users
        try (Connection conn = DBConnection.getConnection()) {
            boolean hasUsers = false;
            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM users");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hasUsers = rs.getInt(1) > 0;
                }
            }

            // If empty, auto-create initial admin account per requirements:
            // "Only the first admin account is created through the login/setup screen"
            if (!hasUsers) {
                String hash = BCrypt.hashpw(normalizedPassword, BCrypt.gensalt());
                String insertSql = "INSERT INTO users (name, email, password_hash, role) VALUES (?, ?, ?, 'ADMIN')";
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setString(1, "Administrator");
                    insertPs.setString(2, normalizedUsername.contains("@") ? normalizedUsername : normalizedUsername + "@dineease.com");
                    insertPs.setString(3, hash);
                    insertPs.executeUpdate();
                }
                return new UserSession("Administrator", normalizedUsername, "ADMIN");
            }

            // Query existing user
            String querySql = "SELECT id, name, email, password_hash, role FROM users WHERE email = ? OR name = ?";
            try (PreparedStatement ps = conn.prepareStatement(querySql)) {
                ps.setString(1, normalizedUsername);
                ps.setString(2, normalizedUsername);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String hash = rs.getString("password_hash");
                        String name = rs.getString("name");
                        String role = rs.getString("role");
                        String email = rs.getString("email");

                        boolean matches = false;
                        if (hash != null && hash.startsWith("$2")) {
                            try {
                                matches = BCrypt.checkpw(normalizedPassword, hash);
                            } catch (Exception ignored) {
                            }
                        } else if (hash != null) {
                            matches = hash.equals(normalizedPassword);
                        }

                        if (matches) {
                            return new UserSession(name, email, role);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("DB login attempt failed: " + e.getMessage());
            // Fallback for offline/first run demo
            if (("admin".equalsIgnoreCase(normalizedUsername) || "admin@dineease.com".equalsIgnoreCase(normalizedUsername))
                    && ("admin123".equals(normalizedPassword) || "password".equals(normalizedPassword) || "admin".equals(normalizedPassword))) {
                return new UserSession("Administrator", "admin@dineease.com", "ADMIN");
            }
            throw new IllegalArgumentException("Database error: " + e.getMessage());
        }

        throw new IllegalArgumentException("Invalid email/username or password.");
    }
}

