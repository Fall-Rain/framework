package com.framework.service;

import com.common.entity.R;
import com.common.file.FileService;
import com.framework.properties.UploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class LocalFileService implements FileService {
    private final UploadProperties.Local LocalUploadProperties;

    public LocalFileService(UploadProperties uploadProperties) {
        this.LocalUploadProperties = uploadProperties.getLocal();
    }

    @Override
    public R upload(InputStream inputStream, String fileName, String uploadDir) {
        return R.ok("https://img.fallrain.cn/04a25b1929e28fea5eebc8b27e4ee39114627125c774bec2c1480c1225622242.png", "上传成功");
    }

    @Override
    public String getType() {
        return "local";
    }
}
