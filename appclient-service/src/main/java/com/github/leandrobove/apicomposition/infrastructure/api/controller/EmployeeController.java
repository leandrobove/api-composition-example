package com.github.leandrobove.apicomposition.infrastructure.api.controller;

import com.github.leandrobove.apicomposition.application.service.EmployeeService;
import com.github.leandrobove.apicomposition.domain.Employee;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Objects;

@RestController
@RequestMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = Objects.requireNonNull(employeeService);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<GetEmployeeDetailsResponse> getEmployeeDetails(@PathVariable("employeeId") final String employeeId) {

        Employee employee = employeeService.findById(employeeId);

        GetEmployeeDetailsResponse responseBody = GetEmployeeDetailsResponse.from(employee);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofMinutes(30)))
                .body(responseBody);
    }
}
