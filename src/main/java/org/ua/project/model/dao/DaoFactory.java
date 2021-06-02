package org.ua.project.model.dao;

import org.ua.project.model.exception.DBException;

public interface DaoFactory {

    UserDao createUserDao();

    CourseDao createCourseDao();

    ThemeDao createThemeDao();

    StudentCourseDao createStudentCourseDao() throws DBException;
}
