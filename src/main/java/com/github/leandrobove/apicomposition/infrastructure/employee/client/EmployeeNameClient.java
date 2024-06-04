package com.github.leandrobove.apicomposition.infrastructure.employee.client;

import com.github.leandrobove.apicomposition.infrastructure.employee.models.GetEmployeeNameResponse;

public interface EmployeeNameClient {

    GetEmployeeNameResponse get(String employeeId);

}
