package com.farmermarket.exception;

public class ResourceNotFoundException
        extends RuntimeException {

    /**
     * Constructor 1:
     * Single message
     *
     * Usage:
     * throw new
     * ResourceNotFoundException(
     *     "User not found!");
     */
    public ResourceNotFoundException(
            String message) {
        super(message);
    }

    /**
     * Constructor 2:
     * Three arguments
     * Builds message automatically!
     *
     * Usage:
     * throw new
     * ResourceNotFoundException(
     *     "User", "id", 5);
     *
     * Message becomes:
     * "User not found with id: 5"
     */
    public ResourceNotFoundException(
            String resourceName,
            String fieldName,
            Object fieldValue) {
        super(String.format(
            "%s not found with %s: %s",
            resourceName,
            fieldName,
            fieldValue));
    }
}
