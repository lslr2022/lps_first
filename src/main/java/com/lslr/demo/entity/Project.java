package com.lslr.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "project2")
public class Project {
    @TableId(value="id", type = IdType.AUTO)
    private  Integer id;
    private String projectname;
    private String projecttype;
    private String projectcondition;
    private String projectmember;
    private String stime;
    private String ltime;


}
