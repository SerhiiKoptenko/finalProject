package org.ua.project.model.exception;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException() {
        super();
    }
}
