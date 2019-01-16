package com.felix.security.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author grez
 * @since 19-1-11
 **/
@SpringBootApplication
@MapperScan(basePackages = {"com.felix.security.jwt.**.*mapper"})
public class SecurityJwtApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApp.class, args);
    }
    /**
     * ajax 跨域设置
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration ajax = new CorsConfiguration();
        ajax.addAllowedOrigin("*");
        ajax.addAllowedHeader("*");
        ajax.addAllowedMethod("*");
        Map<String, CorsConfiguration> map = new LinkedHashMap<>();
        map.put("/**", ajax);
        source.setCorsConfigurations(map);
        return new CorsFilter(source);
    }
}
