package com.github.leandrobove.apicomposition.application.service;

import com.github.leandrobove.apicomposition.domain.Employee;
import com.github.leandrobove.apicomposition.domain.EmployeeGateway;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeGateway employeeGateway;

    public EmployeeService(final EmployeeGateway employeeGateway) {
        this.employeeGateway = Objects.requireNonNull(employeeGateway);
    }

    public Employee findById(final String employeeId) {
        Optional<Employee> employee = this.employeeGateway.findById(employeeId);

        return employee.get();
    }
}