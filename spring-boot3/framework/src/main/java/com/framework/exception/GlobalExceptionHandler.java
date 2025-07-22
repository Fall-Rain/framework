package com.framework.exception;

import com.common.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public R handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        log.error("文件错误", e);
        return R.error("上传文件过大");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleMissingParam(MissingServletRequestParameterException e) {
        log.error("缺少必要参数", e);
        return R.error("缺少必要参数" + e.getParameterName());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error("服务器内部错误", e);
        return R.error("服务器内部错误");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public R handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("找不到路径", e);
        return R.error(404, "找不到路径");
    }


}
