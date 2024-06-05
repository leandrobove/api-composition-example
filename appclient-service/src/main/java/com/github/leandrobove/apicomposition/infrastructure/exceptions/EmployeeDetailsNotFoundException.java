package com.github.leandrobove.apicomposition.infrastructure.exceptions;

public class EmployeeDetailsNotFoundException extends RuntimeException {

    public EmployeeDetailsNotFoundException() {
    }

    public EmployeeDetailsNotFoundException(String message) {
        super(message);
    }

    public EmployeeDetailsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
