package org.ua.project.controller.exception;

/**
 * Exception thrown to indicate that request contains invalid parameter.
 */
public class InvalidRequestParameterException extends Exception {
    private static final long serialVersionUID = -4804071049037340680L;

    public InvalidRequestParameterException() {
        super();
    }

    public InvalidRequestParameterException(Throwable cause) {
        super(cause);
    }
}
