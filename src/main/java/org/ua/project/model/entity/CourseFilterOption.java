package org.ua.project.model.entity;

public class CourseFilterOption {
    private User tutor;
    private Theme theme;

    public CourseFilterOption() {
    }

    public CourseFilterOption(User tutor, Theme theme) {
        this.tutor = tutor;
        this.theme = theme;
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

    public static class Builder {
        private User tutor;
        private Theme theme;

        public Builder setTutor(User tutor) {
            this.tutor = tutor;
            return this;
        }

        public Builder setTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public CourseFilterOption build() {
            return new CourseFilterOption(tutor, theme);
        }
    }
}
