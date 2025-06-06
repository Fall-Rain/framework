package com.framework.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Primary
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
    private String uploadType;
    private Local local = new Local();

    @Data
    public static class Local {
        /**
         * 本地上传的根路径
         */
        private String path;
        private String url;
    }

    @Data
    public static class Oss {
        private String endpoint;
        private String bucketName;
        private String accessKeyId;
        private String accessKeySecret;
        private String url;
    }
}
