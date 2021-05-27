package org.ua.project.model.dao.impl.mapper;

import org.ua.project.model.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements ObjectMapper {

    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("user_id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User.Role role = User.Role.valueOf(resultSet.getString("role"));

        Date date = resultSet.getDate("birth_date");
        LocalDate birthDate = date == null ? null : date.toLocalDate();
        return new User.Builder()
                .setId(id)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setBirthDate(birthDate)
                .setLogin(login)
                .setPassword(password)
                .setRole(role)
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

    public List<User> extractAsList(ResultSet resultSet) throws SQLException{
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            User user = extractFromResultSet(resultSet);
            userList.add(user);
        }
        return userList;
    }
}
