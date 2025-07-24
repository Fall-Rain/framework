package com.admin.sysUser.response;

import com.admin.permission.response.SysRoleResponse;
import lombok.Data;

import java.util.List;

@Data
public class LoginUserResponse {
    private String id;
    private String username;
    private String phone;
    private String name;
    private String avatar;
    private List<String> roleCodes;
}
