package com.example.services;

import com.example.models.User;

public interface UserService {
    User findByUsername(String username);
    void save(User user);
}
