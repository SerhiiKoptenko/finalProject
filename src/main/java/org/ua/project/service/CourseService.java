package org.ua.project.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.*;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalDeletionException;
import org.ua.project.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class CourseService {
    Logger logger = LogManager.getLogger(CourseService.class);

    public List<Course> findAllCourses() {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findAll();
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void createCourse(Course course) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            Optional<User> tutorOpt = Optional.ofNullable(course.getTutor());

            if (tutorOpt.isPresent()) {
                courseDao.createWithTutor(course);
                return;
            }
            courseDao.create(course);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public int getAvailiableCoursesCount(CourseFilterOption courseFilterOption) {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.getFilteredCoursesCount(courseFilterOption);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public List<Course> getAvailableCoursePage(int currentPage, int itemsPerPage,
                                               CourseSortParameter sortParameter, CourseFilterOption filterOption) {
        int offset = itemsPerPage * (currentPage - 1);
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.getFilteredCoursePage(offset, itemsPerPage, sortParameter, filterOption);
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void deleteCourseById(int id) throws IllegalDeletionException, EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            courseDao.delete(id);
        } catch (IllegalDeletionException | EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            throw new ServiceException(e);
        }
    }

    public void updateCourse(Course course) {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            Optional<User> tutorOpt = Optional.ofNullable(course.getTutor());
            if (tutorOpt.isPresent()) {
                courseDao.updateWithTutor(course);
            }

            courseDao.update(course);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public Course findCourseNameById(int id) throws EntityNotFoundException {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findCourseNameById(id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

}
