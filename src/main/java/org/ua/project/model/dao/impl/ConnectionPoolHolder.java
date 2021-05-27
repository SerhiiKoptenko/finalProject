package org.ua.project.model.dao.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                try {
                    InitialContext initialContext = new InitialContext();
                    dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/TestDB");
                } catch (NamingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return dataSource;
    }
}
