package com.admin.permission.service.Impl;

import com.admin.permission.entity.SysRole;
import com.admin.permission.mapper.SysRoleMapper;
import com.admin.permission.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
}
