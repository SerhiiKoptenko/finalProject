package org.ua.project.model.dao;

import org.ua.project.model.exception.DBException;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    void create (T entity) throws DBException;

    T findById(int id) throws DBException;

    List<T> findAll() throws DBException;

    void update(T entity) throws DBException;

    void delete(int id) throws DBException;

    void close() throws DBException;
}