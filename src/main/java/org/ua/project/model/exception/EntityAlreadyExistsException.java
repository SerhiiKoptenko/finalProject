package org.ua.project.model.exception;

public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EntityAlreadyExistsException() {
        super();
    }
}
