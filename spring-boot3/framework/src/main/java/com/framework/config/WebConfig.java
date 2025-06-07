package com.framework.config;

import com.framework.properties.UploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UploadProperties uploadProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
        String path = uploadProperties.getLocal().getPath();
        if (!path.endsWith("/")) {
            path += "/";
        }
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:%s".formatted(path));
    }
}
