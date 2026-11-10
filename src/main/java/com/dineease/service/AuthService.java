package com.dineease.service;

public interface AuthService {
    UserSession login(String username, String password);
}
