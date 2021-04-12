package com.pozharsky.dmitri.exception;

/**
 * An exception that provides information on errors thrown by MailSender object.
 *
 * @author Dmitri Pozharsky
 */
public class MailException extends Exception {

    /**
     * Constructs a MailException object.
     */
    public MailException() {
        super();
    }

    /**
     * Constructs a MailException object with a given message.
     *
     * @param message String object of the given message.
     */
    public MailException(String message) {
        super(message);
    }

    /**
     * Constructs a MailException object with given message and cause.
     *
     * @param message String object of the given message.
     * @param cause   Throwable object of the given cause.
     */
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a MailException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public MailException(Throwable cause) {
        super(cause);
    }
}
