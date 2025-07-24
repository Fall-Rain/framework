package com.admin.permission.mapper;

import com.admin.permission.entity.SysRole;
import com.admin.permission.response.SysRoleResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRoleResponse> selectAll();
}
