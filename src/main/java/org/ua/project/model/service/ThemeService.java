package org.ua.project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.ThemeDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityAlreadyExistsException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.service.exception.ServiceException;

import java.util.List;

/**
 * Service for theme entities.
 */
public class ThemeService {
    private static final Logger logger = LogManager.getLogger(ThemeService.class);

    /**
     * Creates theme specified by Theme entity.
     * @param theme - theme to be created.
     * @throws EntityAlreadyExistsException - if theme already exists.
     */
    public void createTheme(Theme theme) throws EntityAlreadyExistsException {
        try (ThemeDao themeDao = new JDBCDaoFactory().createThemeDao()) {
            themeDao.createTheme(theme);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Finds all themes.
     * @return list of all themes.
     */
    public List<Theme> findAllThemes() {
        try (ThemeDao themeDao = new JDBCDaoFactory().createThemeDao()) {
            return themeDao.findAllThemes();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes theme with specified id.
     * @param id - id of theme to be deleted.
     * @throws EntityNotFoundException - if there is no theme with such id.
     * @throws IllegalDeletionException - if theme cannot be deleted.
     */
    public void deleteTheme(int id) throws EntityNotFoundException, IllegalDeletionException {
        try (ThemeDao themeDao = new JDBCDaoFactory().createThemeDao()) {
            themeDao.deleteTheme(id);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
