package org.ua.project.model.dao.impl.mapper;

import org.ua.project.model.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EntityMapper<T extends Entity> {

    T extractFromResultSet(ResultSet resultSet) throws SQLException;

    List<T> extractAsList(ResultSet resultSet) throws SQLException;

    default T setGeneratedId(PreparedStatement preparedStatement, T entity) throws SQLException{
        ResultSet keys = preparedStatement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        entity.setId(id);
        return entity;
    }
}
