package com.dineease.service;

public class MockAuthService implements AuthService {

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

        String user = normalizedUsername.toLowerCase();
        String pass = normalizedPassword;

        if (("admin".equals(user) || "admin@dineease.com".equals(user) || user.contains("dineease"))
                && ("admin123".equals(pass) || "password".equals(pass))) {
            return new UserSession(normalizedUsername, "Manager");
        }

        throw new IllegalArgumentException("Invalid username or password.");
    }
}
