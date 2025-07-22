package com.admin.permission.service.Impl;

import com.admin.permission.entity.RoleEntity;
import com.admin.permission.mapper.RoleMapper;
import com.admin.permission.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements IRoleService {
}
