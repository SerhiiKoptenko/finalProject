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

public class ThemeService {
    private static final Logger logger = LogManager.getLogger(ThemeService.class);


    public Theme createTheme(Theme theme) throws EntityAlreadyExistsException {
        try (ThemeDao themeDao = new JDBCDaoFactory().createThemeDao()) {
            themeDao.createTheme(theme);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return theme;
    }

    public List<Theme> findAllThemes() {
        try (ThemeDao themeDao = new JDBCDaoFactory().createThemeDao()) {
            return themeDao.findAllThemes();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void deleteTheme(int id) throws EntityNotFoundException, IllegalDeletionException {
        try (ThemeDao themeDao = new JDBCDaoFactory().createThemeDao()) {
            themeDao.deleteTheme(id);
        } catch (EntityNotFoundException | IllegalDeletionException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
