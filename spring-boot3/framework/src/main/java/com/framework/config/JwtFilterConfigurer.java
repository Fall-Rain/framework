package com.framework.config;

import com.framework.filter.JwtAuthenticationFilter;
import com.framework.properties.TokenProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtFilterConfigurer<H extends HttpSecurityBuilder<H>> extends AbstractHttpConfigurer<JwtFilterConfigurer<H>, H> {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public JwtFilterConfigurer(ApplicationContext applicationContext) {
        TokenProperties tokenProperties = applicationContext.getBean(TokenProperties.class);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenProperties);
    }

    @Override
    public void configure(H builder) {
        builder.addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);
    }
}
