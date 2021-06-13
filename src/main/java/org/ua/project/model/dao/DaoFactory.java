package org.ua.project.model.dao;

import org.ua.project.model.exception.DBException;

/**
 * Factory which returns dao objects.
 */
public interface DaoFactory {

    UserDao createUserDao() throws DBException;

    CourseDao createCourseDao() throws DBException;

    ThemeDao createThemeDao() throws DBException;

    StudentCourseDao createStudentCourseDao() throws DBException;
}
