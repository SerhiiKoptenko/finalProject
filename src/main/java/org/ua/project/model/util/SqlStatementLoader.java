package org.ua.project.model.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;


public final class SqlStatementLoader {
    private static final Logger logger = LogManager.getLogger(SqlStatementLoader.class);

    private static volatile SqlStatementLoader instance;

    private final Properties properties = new Properties();
    private static final String PROPERTIES_PATH = "sqlStatements.properties";

    public static SqlStatementLoader getInstance() {
        if (instance == null) {
            synchronized (SqlStatementLoader.class) {
                if (instance == null) {
                    SqlStatementLoader temp = new SqlStatementLoader(PROPERTIES_PATH);
                    instance = temp;
                }
            }
        }
        return instance;
    }

    private SqlStatementLoader(String propertiesPath) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(propertiesPath))  {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Couldn't load sql properties.");
            throw new RuntimeException();
        }
    }

    public String getSqlStatement(String propertyValue) {
        Optional<String> sqlOpt = Optional.ofNullable(properties.getProperty(propertyValue));
        return sqlOpt.orElseThrow(IllegalStateException::new);
    }
}
