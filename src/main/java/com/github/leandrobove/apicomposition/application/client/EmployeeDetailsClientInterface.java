package com.github.leandrobove.apicomposition.application.client;

import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeAddressResponse;
import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeeNameResponse;
import com.github.leandrobove.apicomposition.infrastructure.httpclient.GetEmployeePhoneResponse;

public interface EmployeeDetailsClientInterface {

    GetEmployeeNameResponse getName(String employeeId);
    GetEmployeeAddressResponse getAddress(String employeeId);
    GetEmployeePhoneResponse getPhone(String employeeId);
}
