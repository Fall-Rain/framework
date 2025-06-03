package com.framework.token.Impl;

import com.framework.token.TokenLoadUserService;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractTokenLoadUserService<T> implements TokenLoadUserService<T> {
    private final Class<T> userType;

    @SuppressWarnings("unchecked")
    public AbstractTokenLoadUserService() {
        this.userType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public Class<T> getUserType() {
        return userType;
    }
}
