package com.github.leandrobove.apicomposition.application.client;

import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeNameResponse;

public interface EmployeeNameClient {

    GetEmployeeNameResponse get(String employeeId);

}
