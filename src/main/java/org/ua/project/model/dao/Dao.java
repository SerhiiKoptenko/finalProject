package org.ua.project.model.dao;

import org.ua.project.model.exception.DBException;

/**
 * Dao interface
 */
public interface Dao extends AutoCloseable {

    void close() throws DBException;
}
