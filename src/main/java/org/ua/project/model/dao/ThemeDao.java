package org.ua.project.model.dao;

import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;

import java.util.List;

public interface ThemeDao extends Dao {

    void createTheme(Theme theme) throws DBException;

    Theme findThemeById(int themeId) throws DBException;

    List<Theme> findAllThemes() throws DBException;

    void deleteTheme(int themeId) throws DBException;
}
