package org.ua.project.model.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper {

    Object extractFromResultSet(ResultSet resultSet) throws SQLException;
}
