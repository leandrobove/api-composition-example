package com.github.leandrobove.apicomposition.domain;

public record Address(
        String street,
        String number,
        String zipCode,
        String city,
        String country
) {
}
