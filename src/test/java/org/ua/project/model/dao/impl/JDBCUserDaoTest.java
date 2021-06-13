package org.ua.project.model.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ua.project.model.dao.UserDao;
import org.ua.project.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JDBCUserDaoTest {

    private static final String FIND_USER_BY_ID  = "SELECT id, first_name, last_name, login, role, password, is_blocked " +
                                    "FROM users WHERE id = ?";

    @Before
    public void setUp() throws SQLException {
        JdbcTestUtil.populateTables();
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User.Builder()
                .setId(4)
                .setFirstName("test")
                .setLastName("user")
                .setLogin("test user")
                .setPassword("4444")
                .setRole(User.Role.STUDENT)
                .build();
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCUserDao jdbcUserDao = new JDBCUserDao(connection);
            jdbcUserDao.createUser(user);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            User actual = extractUser(resultSet);
            assertEquals(user, actual);
        }
    }

    private User extractUser(ResultSet resultSet) throws Exception{
        resultSet.next();
        return new User.Builder()
                .setId(resultSet.getInt("id"))
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setLogin(resultSet.getString("login"))
                .setRole(User.Role.valueOf(resultSet.getString("role")))
                .setPassword(resultSet.getString("password"))
                .setBlocked(resultSet.getBoolean("is_blocked"))
                .build();
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        User expected = new User.Builder()
                .setId(1)
                .setFirstName("Tutor")
                .setLastName("A")
                .setLogin("tutorA")
                .setPassword("4444")
                .setRole(User.Role.TUTOR)
                .build();
        try (Connection connection = TestConnectionProvider.getConnection()) {
            JDBCUserDao jdbcUserDao = new JDBCUserDao(connection);
            User actual = jdbcUserDao.findUserByLogin(expected.getLogin());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFindAllUsers() throws Exception {
        User expected1 = new User.Builder()
                .setId(2)
                .setFirstName("Student")
                .setLastName("A")
                .setLogin("studentA")
                .setPassword("4444")
                .setRole(User.Role.STUDENT)
                .build();
        User expected2 = new User.Builder()
                .setId(3)
                .setFirstName("Student")
                .setLastName("B")
                .setLogin("studentB")
                .setPassword("4444")
                .setRole(User.Role.STUDENT)
                .build();
        User expected3 = new User.Builder()
                .setId(1)
                .setFirstName("Tutor")
                .setLastName("A")
                .setLogin("tutorA")
                .setPassword("4444")
                .setRole(User.Role.TUTOR)
                .build();
        List<User> expectedList = new ArrayList<>(Arrays.asList(expected1, expected2, expected3));
        try (Connection connection = TestConnectionProvider.getConnection()) {
            UserDao jdbcUserDao = new JDBCUserDao(connection);
            List<User> actual = jdbcUserDao.findAll();
            assertEquals(expectedList, actual);
        }
    }

    @Test
    public void testUpdateUserBlockedStatus() throws Exception {
        User expected = new User.Builder()
                .setId(2)
                .setFirstName("Student")
                .setLastName("A")
                .setLogin("studentA")
                .setPassword("4444")
                .setRole(User.Role.STUDENT)
                .setBlocked(true)
                .build();
        try (Connection connection = TestConnectionProvider.getConnection()) {
            UserDao userDao = new JDBCUserDao(connection);
            userDao.updateUserBlockedStatus(expected);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, expected.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            User actual = extractUser(resultSet);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFindUsersByRole() throws Exception {
        User expected = new User.Builder()
                .setId(1)
                .setFirstName("Tutor")
                .setLastName("A")
                .setLogin("tutorA")
                .setPassword("4444")
                .setRole(User.Role.TUTOR)
                .build();
        List<User> expectedList = new ArrayList<>(Arrays.asList(expected));
        try (Connection connection = TestConnectionProvider.getConnection()) {
            UserDao userDao = new JDBCUserDao(connection);
            List<User> actualList = userDao.findUsersByRole(User.Role.TUTOR);
            assertEquals(expectedList, actualList);
        }
    }

    @After
    public void tearDown() throws SQLException {
        JdbcTestUtil.truncateAllTables();
    }
}
