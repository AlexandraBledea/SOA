package com.ubbcluj.authentication.exception;

public record ServiceErrorCodes(String errorCode, String errorName, String errorMessage) {

    // 500 Internal Server Error
    public static final ServiceErrorCodes INTERNAL_SERVER_ERROR = new ServiceErrorCodes("SOA-1", "INTERNAL_SERVER_ERROR", "The service encountered an unexpected condition that prevented it from fulfilling the request. Try again later. If the problem persists, report this issue to the service owner.");
    // Thrown when the entity is not found in the database
    public static final ServiceErrorCodes ENTITY_NOT_FOUND = new ServiceErrorCodes("SOA-2", "ENTITY_NOT_FOUND", "Could not find an database entry according to the request.");
    // Thrown when there is a conflict in the database
    public static final ServiceErrorCodes CONFLICT_DATABASE_ENTRY = new ServiceErrorCodes("SOA3", "CONFLICT", "There is an conflicting entry in the database.");
    // Thrown when the authentication fails because of invalid credentials
    public static final ServiceErrorCodes AUTHENTICATION_ERROR = new ServiceErrorCodes("SOA-5", "AUTHENTICATION_FAILED", "Authentication failed. Please check your credentials and try again.");
    // Thrown when the request is not structured in the expected format or contains invalid fields
    public static final ServiceErrorCodes INVALID_REQUEST = new ServiceErrorCodes("SOA-7", "INVALID_REQUEST", "The request is not structured in the expected format or contains invalid fields.");
}
