package com.lslr.demo.controller.dto;

import lombok.Data;
@Data
//接收前端登录请求参数
public class UserDTO {
    private String username;

    private String password;
    private String nickname;
    private String avatar;
    private String token;
    private String newPassword;
    private String name;
}
