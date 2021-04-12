package com.pozharsky.dmitri.exception;

/**
 * An exception that provides information on errors thrown by Dao objects.
 *
 * @author Dmitri Pozharsky
 */
public class DaoException extends Exception {

    /**
     * Constructs a DaoException object.
     */
    public DaoException() {
        super();
    }

    /**
     * Constructs a DaoException object with a given message.
     *
     * @param message String object of the given message.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructs a DaoException object with given message and cause.
     *
     * @param message String object of the given message.
     * @param cause   Throwable object of the given cause.
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a DaoException object with a given cause.
     *
     * @param cause Throwable object of the given cause.
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
