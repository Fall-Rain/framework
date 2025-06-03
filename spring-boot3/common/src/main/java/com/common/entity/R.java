package com.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {
    private Integer code;
    private Boolean success;
    private T result;
    private String message;

    public static <T> R ok(T objects) {
        return new R<T>().setCode(200).setSuccess(true).setResult(objects);
    }

    public static R ok(String message) {
        return new R().setCode(200).setSuccess(true).setMessage(message);
    }

    public static <T> R ok(T objects, String message) {
        return new R<T>().setCode(200).setSuccess(true).setResult(objects).setMessage(message);
    }

    public static R error(Integer code, String message) {
        return new R().setCode(code).setMessage(message).setSuccess(false);
    }



    public static R error(String message) {
        return new R().setCode(500).setMessage(message).setSuccess(false);
    }
}
