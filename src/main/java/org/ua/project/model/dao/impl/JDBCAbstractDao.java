package org.ua.project.model.dao.impl;


import org.ua.project.model.dao.Dao;
import org.ua.project.model.exception.DBException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDBC abstract dao.
 */
public abstract class JDBCAbstractDao implements Dao {
    protected Connection connection;

    protected JDBCAbstractDao(Connection connection) {
        this.connection = connection;
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
