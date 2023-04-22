package com.example.repositories.mysql;

import com.example.models.User;
import com.example.repositories.UserRepository;

import java.sql.*;

public class UserRepositoryMySQL implements UserRepository {
    private final Connection connection;

    public UserRepositoryMySQL() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/demow", "root", "");
    }

    @Override
    public User findByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                return new User(id, username, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
