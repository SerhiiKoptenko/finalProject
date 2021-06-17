package org.ua.project.model.dao.impl;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Class that contains connection pool data source.
 */
public class ConnectionPoolHolder {
    private static volatile DataSource dataSource;

    /**
     * @return data source.
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    try {
                        InitialContext initialContext = new InitialContext();
                        dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/university");
                    } catch (NamingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dataSource;
    }
}
