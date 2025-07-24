package com.admin.sysUser.request;

import com.admin.sysUser.entity.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserRequest extends SysUser {
    private List<String> roleIds;
}
