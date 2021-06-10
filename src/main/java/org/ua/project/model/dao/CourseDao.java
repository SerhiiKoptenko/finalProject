package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.CourseSortParameter;
import org.ua.project.model.entity.Theme;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;

import java.util.List;

public interface CourseDao extends Dao {

    void updateCourse(Course course) throws DBException, EntityNotFoundException;

    void createCourse(Course course) throws DBException, EntityNotFoundException;

    Course findCourseById(int id) throws DBException, EntityNotFoundException;

    void deleteCourse(int id) throws DBException, EntityNotFoundException, IllegalDeletionException;

    int getFilteredCoursesCount(CourseFilterOption filterOption) throws DBException;

    List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;

    List<Course> getFilteredCourses(int offset, int numberOfItems,
                                    CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;
}
