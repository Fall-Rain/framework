package com.admin.userInfo.serviceImpl;

import com.admin.userInfo.response.LoginUserResponse;
import com.admin.userInfo.service.IUserInfoService;
import com.framework.token.Impl.AbstractTokenLoadUserService;
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
