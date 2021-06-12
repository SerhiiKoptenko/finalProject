package org.ua.project.model.entity;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Course implements Entity {
    private static final long serialVersionUID = -190484666951896577L;
    private int id;
    private String name;
    private Theme theme;
    private LocalDate startDate;
    private LocalDate endDate;
    private User tutor;
    private String description;
    private int studentCount;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course() {
    }

    private Course(int id, String name, Theme theme, LocalDate startDate, LocalDate endDate, User tutor, String description, int studentCount) {
        this.id = id;
        this.name = name;
        this.theme = theme;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tutor = tutor;
        this.description = description;
        this.studentCount = studentCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(theme, course.theme) && Objects.equals(startDate, course.startDate) && Objects.equals(endDate, course.endDate) && Objects.equals(tutor, course.tutor) && Objects.equals(description, course.description);
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", theme=" + theme +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", tutor=" + tutor +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private Theme theme;
        private LocalDate startDate;
        private LocalDate endDate;
        private User tutor;
        private String description;
        private int studentCount;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder setTutor(User tutor) {
            this.tutor = tutor;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setStudentCount(int studentCount) {
            this.studentCount = studentCount;
            return this;
        }

        public Course build() {
            return new Course(id, name, theme, startDate, endDate, tutor, description, studentCount);
        }
    }

}
