package com.admin.sysUser.service.impl;

import com.admin.permission.entity.SysUserRole;
import com.admin.permission.service.ISysUserRoleService;
import com.admin.sysUser.request.SysUserRequest;
import com.admin.sysUser.response.LoginUserResponse;
import com.admin.sysUser.response.SysUserResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.admin.sysUser.entity.SysUser;
import com.admin.sysUser.mapper.SysUserMapper;
import com.admin.sysUser.service.ISysUserService;
import com.framework.properties.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService, UserDetailsService {
    private final TokenProperties tokenProperties;
    private final PasswordEncoder passwordEncoder;
    private final ISysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public void generatePassword(SysUser userInfo) {
        String password = passwordEncoder.encode(tokenProperties.getDefaultPassword());
        this.update(Wrappers.<SysUser>lambdaUpdate().set(SysUser::getPassword, password).eq(SysUser::getId, userInfo.getId()));
    }

    @Override
    public LoginUserResponse loadUser(String id) {
        LoginUserResponse loginUserResponse = baseMapper.selectLoginUserById(id);
        return loginUserResponse;
    }

    @Override
    public IPage<SysUserResponse> getList(Page page) {
        return baseMapper.selectAll(page);
    }

    @Override
    public void updateById(SysUserRequest sysUserRequest) {
        sysUserRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, sysUserRequest.getId()));
        sysUserRequest.getRoleIds().stream().map(e -> new SysUserRole()).toList();
        sysUserRoleService.saveBatch(sysUserRequest
                .getRoleIds()
                .stream()
                .map(e -> new SysUserRole()
                        .setUserId(sysUserRequest.getId())
                        .setRoleId(e))
                .collect(Collectors.toList()));
        baseMapper.updateById(sysUserRequest);
    }

    @Override
    public void save(SysUserRequest sysUserRequest) {
        sysUserRoleService.saveBatch(sysUserRequest
                .getRoleIds()
                .stream()
                .map(e -> new SysUserRole()
                        .setUserId(sysUserRequest.getId())
                        .setRoleId(e))
                .collect(Collectors.toList()));
        baseMapper.insert(sysUserRequest);
    }
}
