package com.javawwa25.app.springboot.services;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
