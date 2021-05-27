package org.ua.project.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ua.project.model.dao.CourseDao;
import org.ua.project.model.dao.impl.JDBCCourseDao;
import org.ua.project.model.dao.impl.JDBCDaoFactory;
import org.ua.project.model.entity.Course;

import java.util.List;

public class CourseService {
    Logger logger = LogManager.getLogger(CourseService.class);

    public List<Course> getAllCourses() {
        try (CourseDao courseDao = new JDBCDaoFactory().createCourseDao()) {
            return courseDao.findAll();
        }
    }

}
