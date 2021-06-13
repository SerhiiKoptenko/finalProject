package org.ua.project.model.dao;

import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;

import java.util.List;

/**
 * Dao for theme entity.
 */
public interface ThemeDao extends Dao {

    /**
     * Creates new theme.
     * @param theme - entity which represents theme to be created.
     * @throws DBException - if database error occurs.
     * @throws EntityAlreadyExistsException - if theme with specified id already exists.
     */
    void createTheme(Theme theme) throws DBException, EntityAlreadyExistsException;

    /**
     * Finds all themes.
     * @return list of themes.
     * @throws DBException - if database error occurs.
     */
    List<Theme> findAllThemes() throws DBException;

    /**
     * Deletes existing theme.
     * @param themeId - id of theme to be deleted.
     * @throws DBException - if database error occurs.
     * @throws EntityNotFoundException - if there is no theme with specified id.
     * @throws IllegalDeletionException - if theme cannot be deleted due to foreign key constraint violation.
     */
    void deleteTheme(int themeId) throws DBException, EntityNotFoundException, IllegalDeletionException;
}
