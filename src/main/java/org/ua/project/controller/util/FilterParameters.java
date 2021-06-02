package org.ua.project.controller.util;

import org.ua.project.model.entity.Theme;
import org.ua.project.model.entity.User;

public class FilterParameters {
    private User tutor;
    private Theme theme;

    public FilterParameters(User tutor, Theme theme) {
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
}
