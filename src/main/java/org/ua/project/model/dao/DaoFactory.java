package org.ua.project.model.dao;

public interface DaoFactory {

    UserDao createUserDao();

    CourseDao createCourseDao();
}
