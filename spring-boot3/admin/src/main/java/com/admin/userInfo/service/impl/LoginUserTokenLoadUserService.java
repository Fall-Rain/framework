package com.admin.userInfo.service.impl;

import com.admin.userInfo.response.LoginUserResponse;
import com.admin.userInfo.service.IUserInfoService;
import com.framework.token.AbstractTokenLoadUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserTokenLoadUserService<T> extends AbstractTokenLoadUserService<LoginUserResponse> {
    private final IUserInfoService userInfoService;

    @Override
    public LoginUserResponse loadUser(String uid) {
        return userInfoService.getById(uid);
    }
}
