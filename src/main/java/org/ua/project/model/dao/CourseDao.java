package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.CourseSortParameter;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;

import java.util.List;

public interface CourseDao extends GenericDao<Course> {

    Course findCourseNameById(int id) throws DBException;

    void createWithTutor(Course course) throws DBException;

    void updateWithTutor(Course course) throws DBException;

    int getFilteredCoursesCount(CourseFilterOption filterOption) throws DBException;

    List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;

    List<Course> getFilteredCourses(int offset, int numberOfItems,
                                    CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;
}
