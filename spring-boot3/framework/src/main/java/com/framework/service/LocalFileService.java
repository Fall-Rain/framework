package com.framework.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.common.entity.R;
import com.common.file.FileService;
import com.framework.properties.UploadProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public class LocalFileService implements FileService {
    private final UploadProperties.Local localUploadProperties;
    private final HttpServletRequest request;

    public LocalFileService(UploadProperties uploadProperties, HttpServletRequest request) {
        this.localUploadProperties = uploadProperties.getLocal();
        this.request = request;
    }

    @Override
    public R upload(InputStream inputStream, String fileName, String uploadDir) {
        try {
            // 防止文件名重复，可用雪花算法生成唯一名
            String extName = FileUtil.extName(fileName); // 获取扩展名
            String newFileName = IdWorker.getIdStr() + "." + extName;

            // 拼接保存路径
            String basePath = StrUtil.addSuffixIfNot(localUploadProperties.getPath(), File.separator);
            String saveDir = StrUtil.isBlank(uploadDir) ? basePath : basePath + uploadDir + File.separator;

            // 创建目录
            File dir = new File(saveDir);
            if (!dir.exists()) {
                FileUtil.mkdir(dir);
            }

            // 最终完整路径
            File targetFile = new File(saveDir + newFileName);

            // 保存文件
            IoUtil.copy(inputStream, FileUtil.getOutputStream(targetFile));

            // 构建访问 URL
            String baseUrl = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort();
//            String domain = StrUtil.addSuffixIfNot(localUploadProperties.getUrl(), "/");
            String accessUrl = "%s/upload/%s".formatted(baseUrl, (StrUtil.isBlank(uploadDir) ? newFileName : (uploadDir + "/")) + newFileName);

            return R.ok(accessUrl, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("上传失败：" + e.getMessage());
        }
    }

    @Override
    public String getType() {
        return "local";
    }
}
