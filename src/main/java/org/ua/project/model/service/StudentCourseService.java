package org.ua.project.model.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.StudentCourseDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.service.exception.ServiceException;

import java.util.List;

public class StudentCourseService {
    private static final Logger logger = LogManager.getLogger(StudentCourseService.class);

    public List<StudentCourse> getMarksByStudent(User student, CourseFilterOption courseFilterOption) {
        try (StudentCourseDao studentCourseDao = new JDBCDaoFactory().createStudentCourseDao()) {
            return studentCourseDao.findCoursesByStudent(student, courseFilterOption);
        } catch (DBException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }
}
