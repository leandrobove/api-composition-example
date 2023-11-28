package com.github.leandrobove.apicomposition.infrastructure.httpclient;

import com.github.leandrobove.apicomposition.domain.entity.Address;

public record GetEmployeeAddressResponse(String street, String number, String zipCode, String city, String country) {

    public Address toEntity() {
        return new Address(this.street(), this.number(), this.zipCode(), this.city(), this.country());
    }
}
