package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;

import java.util.List;

public interface CourseDao extends GenericDao<Course> {

    Course findCourseNameById(int id) throws DBException;

    void createWithTutor(Course course) throws DBException;

    int countCourses() throws DBException;

    List<Course> getPageSortedByThemeName(int start, int offset) throws DBException;

    void updateWithTutor(Course course) throws DBException;
}
