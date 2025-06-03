package com.admin.userInfo.service;

import com.admin.userInfo.response.LoginUserResponse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.admin.userInfo.entity.UserInfoEntity;

public interface IUserInfoService extends IService<UserInfoEntity> {
    void generatePassword(UserInfoEntity userInfo);
    LoginUserResponse getById(String id);
}
