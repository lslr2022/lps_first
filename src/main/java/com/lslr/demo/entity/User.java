package com.lslr.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user1")
public class User {
    //@TableId(value="id")
    @TableId(value="id", type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String avatar;
}