package org.ua.project.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.exception.DBException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static final Logger logger = LogManager.getLogger(DBManager.class);

    private static final DBManager instance = new DBManager();
    private DataSource dataSource;

    private DBManager() {
        try {
            InitialContext  initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/TestDB");
        } catch (NamingException e) {
            logger.error(e);
        }
    }

    public Connection getConnection() throws DBException {
        if (dataSource == null) {
            throw new DBException("Data source haven't been initialized");
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBException("Error while getting connection from database", e);
        }
    }

    public static DBManager getInstance() {
        return instance;
    }
}
