package com.admin.sysUser.service;

import com.admin.sysUser.request.SysUserRequest;
import com.admin.sysUser.response.LoginUserResponse;
import com.admin.sysUser.response.SysUserResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.admin.sysUser.entity.SysUser;

public interface ISysUserService extends IService<SysUser> {
    void generatePassword(SysUser userInfo);

    LoginUserResponse loadUser(String id);

    IPage<SysUserResponse> getList(Page page);


    void updateById(SysUserRequest sysUserRequest);

    void save(SysUserRequest sysUserRequest);
}
