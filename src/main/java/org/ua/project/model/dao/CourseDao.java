package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.filter.CourseFilterOption;
import org.ua.project.model.entity.filter.CourseSortParameter;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;

import java.util.List;

/**
 * Dao for course entity.
 */
public interface CourseDao extends Dao {

    /**
     * Updates course using specified entity.
     *
     * @param course - entity to be updated with.
     * @throws DBException             - if database error occurs.
     * @throws EntityNotFoundException - if there is no course with specified id.
     */
    void updateCourse(Course course) throws DBException, EntityNotFoundException;

    /**
     * Creates new course.
     *
     * @param course - entity representing course to be created.
     * @throws DBException             - if database error occurs.
     * @throws EntityNotFoundException - if courses tutor or theme doesn't exist.
     */
    void createCourse(Course course) throws DBException, EntityNotFoundException;

    /**
     * Finds course by its id.
     *
     * @param id - course id.
     * @return course entity.
     * @throws DBException             - if database error occurs.
     * @throws EntityNotFoundException - if there is no course with specified id.
     */
    Course findCourseById(int id) throws DBException, EntityNotFoundException;

    /**
     * Deletes course with specified id.
     *
     * @param id - id of the course to be deleted.
     * @throws DBException              - if database error occurs.
     * @throws EntityNotFoundException  - if there is no course with specified id.
     * @throws IllegalDeletionException - if course cannot be deleted due to constraint violation.
     */
    void deleteCourse(int id) throws DBException, EntityNotFoundException, IllegalDeletionException;

    /**
     * Returns count of courses filtered by specified filter option.
     * @param filterOption - filter option.
     * @return count of courses.
     * @throws DBException - if database error occurs.
     */
    int getFilteredCoursesCount(CourseFilterOption filterOption) throws DBException;

    /**
     * Returns list of courses filtered and sorted by specified sort and filter options.
     *
     * @param sortParameter - course sort parameter.
     * @param filterOption  - course filter option.
     * @return list of courses.
     * @throws DBException - if database error occurs.
     */
    List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;

    /**
     * Returns list of courses of specified size, starting with specified offset, and filtered and sorted by specified sort and filter options.
     *
     * @param offset - offset from which courses should be returned.
     * @param numberOfItems - number of courses to get.
     * @param sortParameter - sort parameter.
     * @param filterOption = filter option.
     * @return list of courses.
     * @throws DBException - if database error occurs.
     */
    List<Course> getFilteredCourses(int offset, int numberOfItems,
                                    CourseSortParameter sortParameter, CourseFilterOption filterOption) throws DBException;
}
