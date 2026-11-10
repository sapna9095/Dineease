package com.dineease.model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private String role; // ADMIN, MANAGER, STAFF
    private Timestamp createdAt;

    // Extra fields for legacy code compatibility
    private String username;
    private String password;
    private String fullName;
    private String status = "Active";

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.email = username;
        this.passwordHash = password;
        this.name = username;
    }

    public User(String fullName, String username, String password, String role, String status) {
        this.fullName = fullName;
        this.name = fullName;
        this.username = username;
        this.email = username;
        this.password = password;
        this.passwordHash = password;
        this.role = role;
        this.status = status;
    }

    public User(int id, String name, String email, String passwordHash, String role, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.fullName = name;
        this.email = email;
        this.username = email;
        this.passwordHash = passwordHash;
        this.password = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }

    public User(String name, String email, String passwordHash, String role) {
        this.name = name;
        this.fullName = name;
        this.email = email;
        this.username = email;
        this.passwordHash = passwordHash;
        this.password = passwordHash;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name != null ? name : fullName; }
    public void setName(String name) { this.name = name; this.fullName = name; }

    public String getEmail() { return email != null ? email : username; }
    public void setEmail(String email) { this.email = email; this.username = email; }

    public String getPasswordHash() { return passwordHash != null ? passwordHash : password; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; this.password = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    // Compatibility methods
    public int getUserId() { return id; }
    public void setUserId(int userId) { this.id = userId; }

    public String getFullName() { return fullName != null ? fullName : name; }
    public void setFullName(String fullName) { this.fullName = fullName; this.name = fullName; }

    public String getUsername() { return username != null ? username : email; }
    public void setUsername(String username) { this.username = username; this.email = username; }

    public String getPassword() { return password != null ? password : passwordHash; }
    public void setPassword(String password) { this.password = password; this.passwordHash = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return getName() + " (" + role + ")";
    }
}
