package org.ua.project.model.dao.mapper;

import org.ua.project.model.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface EntityMapper<T extends Entity> {

    default T makeUnique(Map<Integer, T> cache,
                             T entity) {
        if (entity != null) {
            cache.putIfAbsent(entity.getId(), entity);
            return cache.get(entity.getId());
        }
        return null;
    }

    T extractFromResultSet(ResultSet resultSet) throws SQLException;

    default T setGeneratedId(PreparedStatement preparedStatement, T entity) throws SQLException{
        ResultSet keys = preparedStatement.getGeneratedKeys();
        keys.next();
        int id = keys.getInt(1);
        entity.setId(id);
        return entity;
    }
}
