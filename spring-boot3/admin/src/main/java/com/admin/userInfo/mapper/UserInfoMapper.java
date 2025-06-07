package com.admin.userInfo.mapper;

import com.admin.userInfo.response.LoginUserResponse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.admin.userInfo.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {
    LoginUserResponse selectById(String id);

    UserInfoEntity selectByUsername(@Param("username") String username);
}
