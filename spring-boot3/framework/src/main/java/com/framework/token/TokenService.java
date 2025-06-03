package com.framework.token;

public interface TokenService {
    String generateSystemToken(String uid);
    String getUid();
    String getUid(String token);
}
