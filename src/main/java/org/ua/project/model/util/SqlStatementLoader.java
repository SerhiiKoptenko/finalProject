package org.ua.project.model.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class SqlStatementLoader {
    private static final Logger logger = LogManager.getLogger(SqlStatementLoader.class);
    private final Properties properties = new Properties();

    public SqlStatementLoader(String propertiesPath) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(propertiesPath))  {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Couldn't load sql properties.");
        }
    }

    public String getSqlStatement(String propertyValue) {
        return properties.getProperty(propertyValue);
    }
}
