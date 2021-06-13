package org.ua.project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.filter.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalInsertionException;
import org.ua.project.model.service.exception.ServiceException;

import java.util.List;

/**
 * Service for StudentCourse objects.
 */
public class StudentCourseService {
    private static final Logger logger = LogManager.getLogger(StudentCourseService.class);

    /**
     * Gets StudentCourse list representing courses in which specified student has enrolled.
     * @param student - Student for which courses should be fetched.
     * @param courseFilterOption - filter option.
     * @return StudentCourse list.
     */
    public List<StudentCourse> getCoursesByStudent(User student, CourseFilterOption courseFilterOption) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            return studentCourseDao.findCoursesByStudent(student, courseFilterOption);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets list of students enrolled in specified course.
     * @param course - Course for which students should be fetched.
     * @return StudentCourse list.
     */
    public List<StudentCourse> getStudentsByCourse(Course course) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            logger.debug("fetching students for course {}", course);
            List<StudentCourse> courseStudents = studentCourseDao.findStudentsByCourse(course);
            logger.debug("received students by courses {}", courseStudents);
            return courseStudents;
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Updates students mark.
     * @param studentCourse - contains student, course and mark to be updated.
     */
    public void updateStudentsMark(StudentCourse studentCourse) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
             logger.debug("updating students mark {}", studentCourse);
             studentCourseDao.updateStudentsMark(studentCourse);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Removes specified student from specified course.
     * @param studId - id of student to be removed.
     * @param courseId - id of course from which student should be removed.
     */
    public void removeStudentFromCourse(int studId, int courseId) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            logger.debug("removing student {} from course {}", studId, courseId);
            studentCourseDao.removeStudentFromCourse(studId, courseId);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void enrollStudent(int studId, int courseId) throws IllegalInsertionException, EntityNotFoundException {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            studentCourseDao.enrollStudent(studId, courseId);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
