package org.ua.project.model.exception;

public class EntityAlreadyExistsException extends DBException {

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EntityAlreadyExistsException() {
        super();
    }
}
