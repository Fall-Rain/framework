package com.framework.config;

import com.framework.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.configuration.SpringDocSecurityOAuth2Customizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecturyConfig {
    private final SecurityProperties securityProperties;
    private final ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    /*
     * Security核心配置Filter方法
     * */
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurerAdapterList) throws Exception {
        for (SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> securitySecurityConfigurerAdapter : securityConfigurerAdapterList) {
            httpSecurity.with(securitySecurityConfigurerAdapter, Customizer.withDefaults());
        }

        if (securityProperties.isDisableCsrf()) {
            httpSecurity.csrf(AbstractHttpConfigurer::disable);
        }
        if (securityProperties.isDisableRequestCache()) {
            httpSecurity.requestCache(AbstractHttpConfigurer::disable);
        }
        return httpSecurity
                .build();
    }

    @Bean
    public AuthorizeHttpRequestsConfigurer<HttpSecurity> authorizeHttpRequestsConfigurer() {
        AuthorizeHttpRequestsConfigurer<HttpSecurity> httpSecurityAuthorizeHttpRequestsConfigurer = new AuthorizeHttpRequestsConfigurer<>(applicationContext);
        httpSecurityAuthorizeHttpRequestsConfigurer.getRegistry().anyRequest().permitAll();
        return httpSecurityAuthorizeHttpRequestsConfigurer;
    }

}
