package com.framework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Primary
@Component
@ConfigurationProperties(prefix = "spring.security")
public class SecurityProperties extends org.springframework.boot.autoconfigure.security.SecurityProperties {
    private boolean disableCsrf = true;
    private boolean disableRequestCache = true;
}
