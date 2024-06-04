package com.github.leandrobove.apicomposition.application.client;

import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeePhoneResponse;

public interface EmployeePhoneClient {

    GetEmployeePhoneResponse get(String employeeId);

}
