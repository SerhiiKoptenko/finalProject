package org.ua.project.model.dao;

import org.ua.project.model.entity.Course;
import org.ua.project.model.entity.filter.CourseFilterOption;
import org.ua.project.model.entity.StudentCourse;
import org.ua.project.model.entity.User;
import org.ua.project.model.exception.DBException;
import org.ua.project.model.exception.EntityNotFoundException;
import org.ua.project.model.exception.IllegalInsertionException;

import java.util.List;

/**
 * Dao for student-course relationship.
 */
public interface StudentCourseDao extends Dao {

    /**
     * Finds all courses in which specified student has enrolled.
     * @param student - student for which courses should be found.
     * @param filterOption - option for filtering courses.
     * @return list of courses.
     * @throws DBException - if database error occurs.
     */
    List<StudentCourse> findCoursesByStudent(User student, CourseFilterOption filterOption) throws DBException;

    /**
     * Finds all students which enrolled in specified course.
     * @param course - course for which students should be found.
     * @return list of students.
     * @throws DBException - if database error occurs.
     */
    List<StudentCourse> findStudentsByCourse(Course course) throws DBException;

    /**
     * Updates student mark.
     * @param studentCourse - contains student and course entities for which mark should be updated.
     * @return true
     * @throws DBException - if database error occurs.
     */
    void updateStudentsMark(StudentCourse studentCourse) throws DBException;

    /**
     * Removes student from course.
     * @param studId - id of student to be removed.
     * @param courseId - id of course from which student should be removed.
     * @throws DBException - if database error occurs.
     */
    void removeStudentFromCourse(int studId, int courseId) throws DBException;

    /**
     * Enrolls student in course.
     * @param studId - id of students to be enrolled.
     * @param courseId - id of course for students to be enrolled in.
     * @throws DBException - if database error occurs.
     * @throws EntityNotFoundException - if student and/or course with specified ids doesn't exists.
     * @throws IllegalInsertionException = if students attempts to enroll in course which hash been already completed.
     */
    void enrollStudent(int studId, int courseId) throws DBException, EntityNotFoundException, IllegalInsertionException;
}
