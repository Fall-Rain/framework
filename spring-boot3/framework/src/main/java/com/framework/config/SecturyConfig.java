package com.framework.config;

import com.alibaba.fastjson2.JSONObject;
import com.common.entity.R;
import com.common.token.TokenService;
import com.framework.properties.SecurityProperties;
import com.framework.properties.TokenProperties;
import com.framework.token.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @ConditionalOnMissingBean
    public ExceptionHandlingConfigurer<HttpSecurity> exceptionHandlingConfigurer() {
        ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer = new ExceptionHandlingConfigurer<>();
        httpSecurityExceptionHandlingConfigurer
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(JSONObject.toJSONString(R.error(401, authException.getMessage())));
                }).accessDeniedHandler((request, response, authException) -> {
                    response.setStatus(200);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print(JSONObject.toJSONString(R.error(403, authException.getMessage())));
                });
        return httpSecurityExceptionHandlingConfigurer;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizeHttpRequestsConfigurer<HttpSecurity> authorizeHttpRequestsConfigurer() {
        AuthorizeHttpRequestsConfigurer<HttpSecurity> httpSecurityAuthorizeHttpRequestsConfigurer = new AuthorizeHttpRequestsConfigurer<>(applicationContext);
        httpSecurityAuthorizeHttpRequestsConfigurer.getRegistry().anyRequest().permitAll();
        return httpSecurityAuthorizeHttpRequestsConfigurer;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       List<AuthenticationProvider> authenticationProviderList,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        authenticationProviderList.forEach(authenticationManagerBuilder::authenticationProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HeadersConfigurer<HttpSecurity> headersConfigurer() {
        HeadersConfigurer<HttpSecurity> headersConfigurer = new HeadersConfigurer<HttpSecurity>();
        headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        return headersConfigurer;
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService(TokenProperties tokenProperties, RedisTemplate<Object, Object> redisTemplate) {
        return new TokenServiceImpl(tokenProperties, redisTemplate);
    }

    @Bean
    public JwtFilterConfigurer<HttpSecurity> jwtFilterConfigurer(ApplicationContext applicationContext) {
        return new JwtFilterConfigurer<>(applicationContext);
    }

}
