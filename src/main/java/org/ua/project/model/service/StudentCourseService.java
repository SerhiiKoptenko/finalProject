package org.ua.project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.dao.impl.JDBCStudentCourseDao;
import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class StudentCourseService {
    private static final Logger logger = LogManager.getLogger(StudentCourseService.class);

    public List<StudentCourse> getCoursesByStudent(User student, CourseFilterOption courseFilterOption) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            return studentCourseDao.findCoursesByStudent(student, courseFilterOption);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

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

    public boolean updateStudentsMark(StudentCourse studentCourse) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
             logger.debug("updating students mark {}", studentCourse);
             return studentCourseDao.updateStudentsMark(studentCourse);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public boolean removeStudentFromCourse(StudentCourse studentCourse) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            logger.debug("removing student from course {}", studentCourse);
            return studentCourseDao.removeStudentFromCourse(studentCourse);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
