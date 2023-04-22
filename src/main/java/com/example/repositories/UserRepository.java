package com.example.repositories;

import com.example.models.User;

public interface UserRepository {
    User findByUsername(String username);
    void save(User user);
}
