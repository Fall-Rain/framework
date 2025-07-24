package com.admin.permission.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")
@Accessors(chain = true)
public class SysRole {
    @TableId(type = IdType.ASSIGN_ID) // UUID 主键（也可改为 AUTO 或 INPUT）
    private String id;
    private String name;
    private String code;
    private String description;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
