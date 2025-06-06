package com.admin.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

@Configuration
public class SecturyConfig {
    @Bean
    public AuthorizeHttpRequestsConfigurer<HttpSecurity> authorizeHttpRequestsConfigurer(ApplicationContext applicationContext) {
        AuthorizeHttpRequestsConfigurer<HttpSecurity> httpSecurityAuthorizeHttpRequestsConfigurer = new AuthorizeHttpRequestsConfigurer<>(applicationContext);
        httpSecurityAuthorizeHttpRequestsConfigurer.getRegistry()
                .requestMatchers("/system/login").permitAll()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/druid/**").permitAll()
                .anyRequest().authenticated();
        return httpSecurityAuthorizeHttpRequestsConfigurer;
    }
}
