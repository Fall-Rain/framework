package com.framework.token;

public interface TokenLoadUserService<T> {
    Class<T> getUserType();
    T loadUser(String uid);
}
