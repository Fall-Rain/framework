package com.admin.userInfo.service.impl;

import com.admin.userInfo.response.LoginUserResponse;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.userInfo.entity.UserInfoEntity;
import com.admin.userInfo.mapper.UserInfoMapper;
import com.admin.userInfo.service.IUserInfoService;
import com.framework.properties.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements IUserInfoService, UserDetailsService {
    private final TokenProperties tokenProperties;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public void generatePassword(UserInfoEntity userInfo) {
        String password = passwordEncoder.encode(tokenProperties.getDefaultPassword());
        this.update(Wrappers.<UserInfoEntity>lambdaUpdate().set(UserInfoEntity::getPassword, password).eq(UserInfoEntity::getId, userInfo.getId()));
    }

    @Override
    public LoginUserResponse getById(String id) {
        LoginUserResponse loginUserResponse = baseMapper.selectById(id);
        return loginUserResponse;
    }
}
