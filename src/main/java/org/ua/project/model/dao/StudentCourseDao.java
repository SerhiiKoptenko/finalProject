package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalInsertionException;

import java.util.List;

public interface StudentCourseDao extends Dao {
    List<StudentCourse> findCoursesByStudent(User student, CourseFilterOption filterOption) throws DBException;

    List<StudentCourse> findStudentsByCourse(Course course) throws DBException;

    boolean updateStudentsMark(StudentCourse studentCourse) throws DBException;

    boolean removeStudentFromCourse(int studId, int courseId) throws DBException;

    void enrollStudent(int studId, int courseId) throws DBException, EntityNotFoundException, IllegalInsertionException;
}
