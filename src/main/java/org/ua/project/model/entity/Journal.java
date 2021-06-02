package org.ua.project.model.entity;

public class Journal {
    private int studIt;
    private int courseId;
    private int mark;

    public Journal(int studIt, int courseId, int mark) {
        this.studIt = studIt;
        this.courseId = courseId;
        this.mark = mark;
    }

    public Journal() {
    }

    public int getStudIt() {
        return studIt;
    }

    public void setStudIt(int studIt) {
        this.studIt = studIt;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public static class Builder {
        private int studIt;
        private int courseId;
        private int mark;

        public Builder setStudId(int studIt) {
            this.studIt = studIt;
            return this;
        }

        public Builder setCourseId(int courseId) {
            this.courseId = courseId;
            return this;
        }

        public Builder setMark(int mark) {
            this.mark = mark;
            return this;
        }

        public Journal build() {
            return new Journal(studIt, courseId, mark);
        }
    }
}
