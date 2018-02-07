package com.xterr.tradereporting.model;

/**
 * Represent an exception related to data
 */
public class ModelException extends Exception {

    ModelException(String message) {
        super(message);
    }

    public ModelException(String message, Exception ex) {
        super(message, ex);
    }

}
