package org.ua.project.service.exception;

public class FailedToEnrollStudentException extends Exception {
    public FailedToEnrollStudentException() {
        super();
    }

    public FailedToEnrollStudentException(String message) {
        super(message);
    }

    public FailedToEnrollStudentException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedToEnrollStudentException(Throwable cause) {
        super(cause);
    }
}
