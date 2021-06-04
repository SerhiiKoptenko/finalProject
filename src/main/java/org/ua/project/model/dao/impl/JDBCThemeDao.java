package org.ua.project.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.ThemeDao;
import org.ua.project.model.dao.mapper.ThemeMapper;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.util.SqlStatementLoader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCThemeDao implements ThemeDao {
    private static final Logger logger = LogManager.getLogger(JDBCThemeDao.class);

    private final Connection connection;

    private static final String FIND_ALL_THEMES;
    private static final String CREATE_THEME;
    private static final String DELETE_THEME;
    private static final String FIND_THEME_BY_ID;

    static {
        SqlStatementLoader loader = SqlStatementLoader.getInstance();
        FIND_ALL_THEMES = loader.getSqlStatement("findAllThemes");
        CREATE_THEME = loader.getSqlStatement("createTheme");
        DELETE_THEME = loader.getSqlStatement("deleteTheme");
        FIND_THEME_BY_ID = loader.getSqlStatement("findThemeById");
    }

    public JDBCThemeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Theme theme) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_THEME)) {
            preparedStatement.setString(1, theme.getName());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new EntityAlreadyExistsException();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public Theme findById(int id) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_THEME_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new EntityNotFoundException();
            }
            return new ThemeMapper().extract(resultSet);
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public List<Theme> findAll() throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_THEMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ThemeMapper themeMapper = new ThemeMapper();
            List<Theme> themesList = new ArrayList<>();
            while (resultSet.next()) {
                themesList.add(themeMapper.extract(resultSet));
            }
            return themesList;
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void update(Theme entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) throws DBException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_THEME)) {
            preparedStatement.setInt(1, id);
            if (preparedStatement.executeUpdate() == 0) {
                throw new EntityNotFoundException();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalDeletionException();
        } catch (SQLException e) {
            logger.error(e);
            throw new DBException(e);
        }
    }

    @Override
    public void close() throws DBException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
