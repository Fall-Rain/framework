package com.admin.permission.mapper;

import com.admin.permission.entity.RoleEntity;
import com.admin.permission.response.RoleResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleEntity> {
   List<RoleResponse> selectAll();
}
