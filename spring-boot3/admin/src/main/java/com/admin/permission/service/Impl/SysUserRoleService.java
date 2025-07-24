package com.admin.permission.service.Impl;

import com.admin.permission.entity.SysUserRole;
import com.admin.permission.mapper.SysUserRoleMapper;
import com.admin.permission.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
}
