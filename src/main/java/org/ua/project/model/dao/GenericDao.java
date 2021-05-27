package org.ua.project.model.dao;

import org.ua.project.model.exception.DBException;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<T> extends AutoCloseable {

    void create (T entity) throws DBException;

    T findById(int id);

    List<T> findAll();

    void update(T entity);

    void delete(int id);

    void close();
}