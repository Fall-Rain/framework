package com.framework.utils;

import com.common.token.TokenLoadUserService;
import com.common.token.TokenService;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class TokenUtils {
    private static TokenService tokenService;
    private static final Map<Class<?>, TokenLoadUserService<?>> serviceMap = new ConcurrentHashMap<>();

    public TokenUtils(TokenService tokenService, List<TokenLoadUserService<?>> serviceList) {
        TokenUtils.tokenService = tokenService;
        serviceList.forEach(tokenLoadUserService -> serviceMap.put(tokenLoadUserService.getUserType(), tokenLoadUserService));
    }

    public static String generateSystemToken(String uid) {
        return tokenService.generateSystemToken(uid);
    }

    public static String getUid() {
        return tokenService.getUid();
    }

    public static String getUid(String token) {
        return tokenService.getUid(token);
    }


    public static <T> T getUser(String uid, Class<T> clazz) {
        TokenLoadUserService<T> service = (TokenLoadUserService<T>) serviceMap.get(clazz);
        if (service == null) {
            throw new IllegalArgumentException("No TokenLoadUserService registered for: " + clazz.getName());
        }
        return service.loadUser(getUid(uid));
    }

    public static <T> T getUser(Class<T> clazz) {
        TokenLoadUserService<T> service = (TokenLoadUserService<T>) serviceMap.get(clazz);
        if (service == null) {
            throw new IllegalArgumentException("No TokenLoadUserService registered for: " + clazz.getName());
        }
        return service.loadUser(getUid());
    }
}
