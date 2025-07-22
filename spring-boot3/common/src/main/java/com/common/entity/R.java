package com.common.entity;

public record R<T>(
        Integer code,
        Boolean success,
        T result,
        String message
) {

    public static <T> R<T> ok(T data) {
        return new R<>(200, true, data, null);
    }

    public static <T> R<T> ok(T data, String message) {
        return new R<>(200, true, data, message);
    }

    public static R<Void> ok(String message) {
        return new R<>(200, true, null, message);
    }

    public static <T> R<T> error(Integer code, String message) {
        return new R<>(code, false, null, message);
    }

    public static <T> R<T> error(String message) {
        return new R<>(500, false, null, message);
    }
}
