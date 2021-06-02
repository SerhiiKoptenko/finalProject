package org.ua.project.model.entity;

public class CourseFilterOption {
    private User tutor;
    private Theme theme;
    private User student;
    private CourseStatus courseStatus;

    public CourseFilterOption() {
    }

    public CourseFilterOption(User tutor, Theme theme, User user, CourseStatus courseStatus) {
        this.tutor = tutor;
        this.theme = theme;
        this.student = user;
        this.courseStatus = courseStatus;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public static class Builder {
        private User tutor;
        private Theme theme;
        private User student;
        private CourseStatus courseStatus;

        public Builder setTutor(User tutor) {
            this.tutor = tutor;
            return this;
        }

        public Builder setTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder setStudent(User student) {
            this.student = student;
            return this;
        }

        public Builder setCourseStatus(CourseStatus courseStatus) {
            this.courseStatus = courseStatus;
            return this;
        }


        public CourseFilterOption build() {
            return new CourseFilterOption(tutor, theme, student, courseStatus);
        }
    }

    public enum CourseStatus {
        ONGOING,
        COMPLETED,
        NOT_STARTED,
    }
}
