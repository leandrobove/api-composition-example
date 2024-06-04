package com.github.leandrobove.apicomposition.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.Duration;

import static org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer;

@EnableCaching
@Configuration(proxyBeanMethods = false)
public class CacheConfig {

    @Value("${app.cache.ttl}")
    private Duration ttl;

    public final static String EMPLOYEE_NAME_CACHE = "employee-name";
    public final static String EMPLOYEE_ADDRESS_CACHE = "employee-address";
    public final static String EMPLOYEE_PHONE_CACHE = "employee-phone";

    @Bean
    public RedisCacheConfiguration defaultCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.ttl)
                .disableCachingNullValues()
                .serializeValuesWith(fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
