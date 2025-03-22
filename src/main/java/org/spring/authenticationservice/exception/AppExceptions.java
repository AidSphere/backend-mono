package org.spring.authenticationservice.exception;

import java.util.Map;

/**
 * Application exception classes
 */
public class AppExceptions {

    /**
     * Base exception class for all application-specific exceptions
     */
    public static class ApplicationException extends RuntimeException {
        public ApplicationException(String message) {
            super(message);
        }

        public ApplicationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when a requested resource is not found
     */
    public static class ResourceNotFoundException extends ApplicationException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when attempting to create a resource that already exists
     */
    public static class ResourceAlreadyExistsException extends ApplicationException {
        public ResourceAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when file storage operations fail
     */
    public static class FileStorageException extends ApplicationException {
        public FileStorageException(String message) {
            super(message);
        }

        public FileStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when token validation fails
     */
    public static class InvalidTokenException extends ApplicationException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when email sending fails
     */
    public static class EmailSendingException extends ApplicationException {
        public EmailSendingException(String message) {
            super(message);
        }

        public EmailSendingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when authentication or authorization fails
     */
    public static class AuthenticationException extends ApplicationException {
        public AuthenticationException(String message) {
            super(message);
        }

        public AuthenticationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when validation fails
     */
    public static class ValidationException extends ApplicationException {
        private final Map<String, String> errors;

        public ValidationException(String message, Map<String, String> errors) {
            super(message);
            this.errors = errors;
        }

        public Map<String, String> getErrors() {
            return errors;
        }
    }
}