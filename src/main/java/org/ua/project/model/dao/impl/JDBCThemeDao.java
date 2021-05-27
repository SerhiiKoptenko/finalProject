package org.ua.project.model.dao.impl;

import org.ua.project.model.dao.ThemeDao;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JDBCThemeDao implements ThemeDao {
    private Connection connection;

    private static final String FIND_ALL_THEMES;

    static {
        SqlStatementLoader loader = new SqlStatementLoader("sqlStatements.properties");
        FIND_ALL_THEMES = loader.getSqlStatement("findAllThemes");
    }

    public JDBCThemeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Theme entity) throws DBException {

    }

    @Override
    public Theme findById(int id) {
        return null;
    }

    @Override
    public List<Theme> findAll() {
       return null;
    }

    @Override
    public void update(Theme entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
