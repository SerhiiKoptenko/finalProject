package org.ua.project.model.dao.mapper;

import org.ua.project.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements EntityMapper<User> {

    @Override
    public User extract(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User.Role role = User.Role.valueOf(resultSet.getString("role"));
        boolean isBlocked = resultSet.getBoolean("is_blocked");

        return new User.Builder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setLogin(login)
                .setPassword(password)
                .setRole(role)
                .setBlocked(isBlocked)
                .build();
    }

    public User extractAuthorizationData(ResultSet resultSet) throws SQLException {
        String password = resultSet.getString("password");
        User.Role role = User.Role.valueOf(resultSet.getString("role"));

        return new User.Builder()
                .setPassword(password)
                .setRole(role)
                .build();
    }

    public User extractStudentData(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int studId = resultSet.getInt("stud_id");
        return new User.Builder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setId(studId)
                .build();
    }
}
