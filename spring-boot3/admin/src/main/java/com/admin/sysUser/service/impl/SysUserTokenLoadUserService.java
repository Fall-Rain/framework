package com.admin.sysUser.service.impl;

import com.admin.sysUser.response.LoginUserResponse;
import com.admin.sysUser.service.ISysUserService;
import com.framework.token.AbstractTokenLoadUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SysUserTokenLoadUserService extends AbstractTokenLoadUserService<LoginUserResponse> {
    private final ISysUserService userInfoService;

    @Override
    public LoginUserResponse loadUser(String uid) {
        return userInfoService.loadUser(uid);
    }
}
