package org.ua.project.model.exception;


 /**
  * Thrown if entity with unique field already exists.
  */
public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public EntityAlreadyExistsException() {
        super();
    }
}
