package com.github.leandrobove.apicomposition.infrastructure.employee.client;

import com.github.leandrobove.apicomposition.infrastructure.employee.models.GetEmployeeAddressResponse;

public interface EmployeeAddressClient {

    GetEmployeeAddressResponse get(String employeeId);

}
