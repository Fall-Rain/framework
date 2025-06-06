package com.admin.userInfo.response;

import lombok.Data;

@Data
public class LoginUserResponse {
    private String id;
    private String username;
    private String phone;
    private String name;
}
