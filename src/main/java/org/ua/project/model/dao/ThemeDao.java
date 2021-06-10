package org.ua.project.model.dao;

import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;

import java.util.List;

public interface ThemeDao extends Dao {

    void createTheme(Theme theme) throws DBException, EntityAlreadyExistsException;

    List<Theme> findAllThemes() throws DBException;

    void deleteTheme(int themeId) throws DBException, EntityNotFoundException, IllegalDeletionException;
}
