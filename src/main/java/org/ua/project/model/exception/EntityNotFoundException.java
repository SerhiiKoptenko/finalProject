package org.ua.project.model.exception;

/**
 * Thrown if there is no entity matching specified field value.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException() {
        super();
    }
}
