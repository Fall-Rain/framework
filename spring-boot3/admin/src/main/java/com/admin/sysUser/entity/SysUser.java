package com.admin.sysUser.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@TableName("sys_user")
@Accessors(chain = true)
public class SysUser implements UserDetails {
    @TableId(type = IdType.ASSIGN_ID) // UUID 主键（也可改为 AUTO 或 INPUT）
    private String id;

    private String username;
    @TableField(exist = false)
    private String password;

    private String phone;
    private String name;
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
