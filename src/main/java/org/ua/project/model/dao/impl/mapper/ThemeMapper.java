package org.ua.project.model.dao.impl.mapper;

import org.ua.project.model.entity.Theme;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThemeMapper implements EntityMapper {

    @Override
    public Theme extractFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Theme.Builder()
                .setId(id)
                .setName(name)
                .build();
    }

    @Override
    public List<Theme> extractAsList(ResultSet resultSet) throws SQLException {
        List<Theme> themes = new ArrayList<>();
        while (resultSet.next()) {
            Theme theme = extractFromResultSet(resultSet);
            themes.add(theme);
        }
        return themes;
    }
}
