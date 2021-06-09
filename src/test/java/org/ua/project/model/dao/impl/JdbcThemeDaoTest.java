package org.ua.project.model.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ua.project.model.dao.ThemeDao;
import org.ua.project.model.dao.mapper.ThemeMapper;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JdbcThemeDaoTest {
    private static String FIND_THEME_BY_ID = "SELECT id, name FROM themes " +
            "WHERE id = ?";
    @Before
    public void setUp() throws SQLException {
        JdbcTestUtil.populateTables();
    }

    @Test
    public void testCreateTheme() throws SQLException, DBException {
        Theme expected = new Theme(4, "Theme D");
        try (Connection connection = TestConnectionProvider.getConnection()) {
            ThemeDao themeDao = new JDBCThemeDao(connection);
            themeDao.createTheme(expected);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_THEME_BY_ID);
            preparedStatement.setInt(1, expected.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Theme actual = extractTheme(resultSet);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFindThemeById() throws SQLException, DBException {
        Theme expected = new Theme(1, "Theme A");
        try (Connection connection = TestConnectionProvider.getConnection()) {
            ThemeDao themeDao = new JDBCThemeDao(connection);
            Theme actual = themeDao.findThemeById(expected.getId());
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testFindAllThemes() throws SQLException, DBException {
        Theme themeA = new Theme(1, "Theme A");
        Theme themeB = new Theme(2, "Theme B");
        Theme themeC = new Theme(3, "Theme C");
        List<Theme> expectedList = new ArrayList<>(Arrays.asList(themeA, themeB, themeC));
        try (Connection connection = TestConnectionProvider.getConnection()) {
            ThemeDao themeDao = new JDBCThemeDao(connection);
            List<Theme> actualList = themeDao.findAllThemes();
            assertEquals(expectedList, actualList);
        }
    }

    @Test
    public void testDeleteTheme() throws SQLException, DBException {
        int themeId = 3;
        try (Connection connection = TestConnectionProvider.getConnection()) {
            ThemeDao themeDao = new JDBCThemeDao(connection);
            themeDao.deleteTheme(3);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_THEME_BY_ID);
            preparedStatement.setInt(1, themeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            assertFalse(resultSet.next());
        }
    }

    private Theme extractTheme(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return new Theme.Builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .build();
    }

    @After
    public void tearDown() throws SQLException {
        JdbcTestUtil.truncateAllTables();
    }
}
