package org.ua.project.controller.exception;

public class InvalidDataException  extends RuntimeException {
    private static final long serialVersionUID = 3683196774298608801L;

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException() {
        super();
    }
}
