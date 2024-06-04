package com.github.leandrobove.apicomposition.application.client;

import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeAddressResponse;

public interface EmployeeAddressClient {

    GetEmployeeAddressResponse get(String employeeId);

}
