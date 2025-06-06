package com.common.token;

public interface TokenLoadUserService<T> {
    Class<T> getUserType();
    T loadUser(String uid);
}
