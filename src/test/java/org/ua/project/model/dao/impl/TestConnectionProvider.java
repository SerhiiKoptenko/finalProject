package org.ua.project.model.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionProvider {
    private TestConnectionProvider(){}

    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection("jdbc:mysql://localhost/test_db?allowMultiQueries=true&" + "user=user-1111&password=1111");
    }
}
