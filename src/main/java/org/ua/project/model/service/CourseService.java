package org.ua.project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.*;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.model.service.exception.ServiceException;

import java.util.List;

public class CourseService {
    Logger logger = LogManager.getLogger(CourseService.class);

    /**
     * Creates new course.
     * @param course - entity representing course to be created.
     * @throws EntityNotFoundException - if theme id is not found in database.
     */
    public void createCourse(Course course) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.createCourse(course);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Counts all courses filtered according to filter option.
     * @param filterOption - filter option which contains filter parameters.
     * @return - courses count.
     */
    public int getAvailableCoursesCount(CourseFilterOption filterOption) {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            logger.debug("getting courses count with following filter option: {}", filterOption);
            int courseCount =  courseDao.getFilteredCoursesCount(filterOption);
            logger.debug("courses count: {}", courseCount);
            return courseCount;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Returns list of courses filtered by filter option and sorted according to sort parameter.
     * @param sortParameter - parameter for sorting courses in list.
     * @param filterOption - parameter for filtering courses.
     * @return list of courses.
     */
    public List<Course> getFilteredCourses(CourseSortParameter sortParameter, CourseFilterOption filterOption) {
        logger.debug("getting courses with following filter option{}", filterOption);
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            List<Course> courses = courseDao.getFilteredCourses(sortParameter, filterOption);
            logger.debug("received courses: {}", courses);
            return courses;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Returns list of courses representing one page.
     * @param currentPage - page to be retrieved.
     * @param itemsPerPage - number of items to be displayed on page.
     * @param sortParameter - parameter for sorting courses in list.
     * @param filterOption parameter for filtering courses.
     * @return courses page.
     */
    public List<Course> getFilteredCoursesPage(int currentPage, int itemsPerPage,
                                               CourseSortParameter sortParameter, CourseFilterOption filterOption) {
        int offset = itemsPerPage * (currentPage - 1);
        logger.debug("getting courses page with following filter option{}", filterOption);
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            List<Course> courses = courseDao.getFilteredCourses(offset, itemsPerPage, sortParameter, filterOption);
            logger.debug("received courses page: {}", courses);
            return courses;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Fetches course from the database by id.
     * @param id - id of the course to be retrieved.
     * @return course entity.
     * @throws EntityNotFoundException - if there is no course with specified id.
     */
    public Course findCourseById(int id) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findCourseById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Deletes course by its id.
     * @param id - id of the course to be deleted.
     * @throws IllegalDeletionException - if course can't be deleted.
     * @throws EntityNotFoundException - if there no course with specified id.
     */
    public void deleteCourseById(int id) throws IllegalDeletionException, EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.deleteCourse(id);
        } catch (IllegalDeletionException | EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Updates course with data contained in course entity.
     * @param course - entity representing course to be updated.
     */
    public void updateCourse(Course course) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.updateCourse(course);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

}
