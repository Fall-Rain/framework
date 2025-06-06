package com.framework.utils;

import com.common.entity.R;
import com.common.file.FileService;
import com.framework.properties.UploadProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FileUtils {
    private static Map<String, FileService> fileServiceMap;
    private static UploadProperties uploadProperties;

    private final List<FileService> fileServices;
    private final UploadProperties configuredUploadProperties;

    public FileUtils(List<FileService> fileServices, UploadProperties uploadProperties) {
        this.fileServices = fileServices;
        this.configuredUploadProperties = uploadProperties;
    }

    @PostConstruct
    public void init() {
        uploadProperties = configuredUploadProperties;
        fileServiceMap = fileServices.stream()
                .collect(Collectors.toMap(FileService::getType, e -> e));
    }

    public static R upload(InputStream inputStream, String fileName, String uploadDir) {
        return fileServiceMap.get(uploadProperties.getUploadType()).upload(inputStream, fileName, uploadDir);
    }
}
