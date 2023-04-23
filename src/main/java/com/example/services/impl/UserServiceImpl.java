package com.example.services.impl;

import com.example.models.User;
import com.example.repositories.UserRepository;
import com.example.repositories.mysql.UserRepositoryMySQL;
import com.example.services.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    public static UserServiceImpl createUserServiceImpl() throws SQLException {
		return new UserServiceImpl();
	}

	private final UserRepository userRepository;

    private UserServiceImpl() throws SQLException {
        this.userRepository = new UserRepositoryMySQL();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
