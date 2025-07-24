package com.admin.permission.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("sys_user_role")
@Accessors(chain = true)
public class SysUserRole {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String userId;
    private String roleId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
