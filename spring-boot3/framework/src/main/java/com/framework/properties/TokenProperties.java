package com.framework.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Primary
@Component
@ConfigurationProperties(prefix = "spring.security.token")
public class TokenProperties {
    private String secretKey;
    private String defaultPassword;
    private String tokenHeader;
    private String subject;
    private String userPos;
    private Integer tokenTime;
    private String tokenPos;
}
