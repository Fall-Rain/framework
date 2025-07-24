package com.admin.sysUser.mapper;

import com.admin.sysUser.response.LoginUserResponse;
import com.admin.sysUser.response.SysUserResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.admin.sysUser.entity.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {
    LoginUserResponse selectLoginUserById(String id);

    IPage<SysUserResponse> selectAll(@Param("page") Page page);

    SysUser selectByUsername(@Param("username") String username);


}
