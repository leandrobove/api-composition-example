package com.github.leandrobove.apicomposition.infrastructure.api.exception;

import com.github.leandrobove.apicomposition.infrastructure.exceptions.EmployeeDetailsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmployeeApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmployeeDetailsNotFoundException.class)
    public ResponseEntity<ProblemDetails> handleEmployeeDetailsNotFoundException(EmployeeDetailsNotFoundException detailsNotFoundException) {
        ProblemDetails problemDetail = new ProblemDetails(HttpStatus.SERVICE_UNAVAILABLE.value(), detailsNotFoundException.getMessage());

        return ResponseEntity.status(problemDetail.status()).body(problemDetail);
    }
}


