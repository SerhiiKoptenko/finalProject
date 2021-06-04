package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;

import java.util.List;

public interface StudentCourseDao extends GenericDao<StudentCourse> {
    List<StudentCourse> findCoursesByStudent(User student, CourseFilterOption filterOption) throws DBException;

    List<StudentCourse> findStudentsByCourse(Course course) throws DBException;

    boolean updateStudentsMark(StudentCourse studentCourse) throws DBException;

    boolean removeStudentFromCourse(StudentCourse studentCourses) throws DBException;
}
