package com.github.leandrobove.apicomposition.infrastructure.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfig {

    public final static String EMPLOYEE_NAME_CACHE = "employee-name";
    public final static String EMPLOYEE_ADDRESS_CACHE = "employee-address";
    public final static String EMPLOYEE_PHONE_CACHE = "employee-phone";

}
