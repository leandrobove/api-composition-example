package com.github.leandrobove.apicomposition.infrastructure.employee.client;

import com.github.leandrobove.apicomposition.infrastructure.employee.models.GetEmployeePhoneResponse;

public interface EmployeePhoneClient {

    GetEmployeePhoneResponse get(String employeeId);

}
