package com.admin.userInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("user_info")
@Accessors(chain = true)
public class UserInfoEntity {
    @TableId(type = IdType.ASSIGN_ID) // UUID 主键（也可改为 AUTO 或 INPUT）
    private String id;

    private String username;

    private String password;

    private String phone;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
