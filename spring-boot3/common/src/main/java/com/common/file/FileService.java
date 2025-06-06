package com.common.file;

import com.common.entity.R;

import java.io.InputStream;

public interface FileService {
    R upload(InputStream inputStream, String fileName,String uploadDir);
    String getType();
}
