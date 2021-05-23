package org.ua.project.controller.constants;

public enum Parameter {
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    LOGIN("login"),
    PASSWORD("password");

    private String value;

    Parameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
