package com.github.leandrobove.apicomposition.domain;

import java.util.Optional;

public interface EmployeeGateway {

    Optional<Employee> findById(String employeeId);

}
