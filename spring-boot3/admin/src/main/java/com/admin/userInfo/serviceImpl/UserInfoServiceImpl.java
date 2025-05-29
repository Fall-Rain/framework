package com.admin.userInfo.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.userInfo.entity.UserInfoEntity;
import com.admin.userInfo.mapper.UserInfoMapper;
import com.admin.userInfo.service.IUserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements IUserInfoService {
}
