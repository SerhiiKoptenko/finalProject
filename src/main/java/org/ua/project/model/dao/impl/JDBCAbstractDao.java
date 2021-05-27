package org.ua.project.model.dao.impl;


import java.sql.Connection;

public abstract class JDBCAbstractDao  {
    protected Connection connection;

    public JDBCAbstractDao(Connection connection) {
        this.connection = connection;
    }
}
