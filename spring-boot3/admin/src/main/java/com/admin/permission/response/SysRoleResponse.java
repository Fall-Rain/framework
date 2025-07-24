package com.admin.permission.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysRoleResponse {
    @TableId(type = IdType.ASSIGN_ID) // UUID 主键（也可改为 AUTO 或 INPUT）
    private String id;
    private String name;
    private String code;
    private String description;
}
