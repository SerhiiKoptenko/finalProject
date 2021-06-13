package org.ua.project.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Object representing student and course many to many relationship and containing students mark.
 */
public class StudentCourse implements Serializable {
    private static final long serialVersionUID = 5289992206095326260L;
    private User student;
    private Course course;
    private int mark;

    public StudentCourse(User student, Course course, int mark) {
        this.student = student;
        this.course = course;
        this.mark = mark;
    }

    public StudentCourse() {
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return mark == that.mark && Objects.equals(student, that.student) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course, mark);
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "student=" + student +
                ", course=" + course +
                ", mark=" + mark +
                '}';
    }

    public static class Builder {
        private User studIt;
        private Course course;
        private int mark;

        public Builder setStudent(User studIt) {
            this.studIt = studIt;
            return this;
        }

        public Builder setCourse(Course courseId) {
            this.course = courseId;
            return this;
        }

        public Builder setMark(int mark) {
            this.mark = mark;
            return this;
        }

        public StudentCourse build() {
            return new StudentCourse(studIt, course, mark);
        }
    }
}
