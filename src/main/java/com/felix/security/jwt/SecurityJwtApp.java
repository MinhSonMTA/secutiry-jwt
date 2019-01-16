package com.felix.security.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author grez
 * @since 19-1-11
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.whuaz.security.jwt.**.*mapper"})
public class SecurityJwtApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApp.class, args);
    }
}
