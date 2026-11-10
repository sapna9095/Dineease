package com.dineease.service;

public class UserSession {
    private final String username;
    private final String email;
    private final String role;

    public UserSession(String username, String role) {
        this.username = username;
        this.email = username;
        this.role = role;
    }

    public UserSession(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
