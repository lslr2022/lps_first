package com.lslr.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.lslr.demo.common.Result;
import com.lslr.demo.entity.User;
import com.lslr.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private UserService userService;

    //实验1
    @GetMapping("/column")
    public Result get(){
        Map<String,Object> map=new HashMap<>();
        map.put("x", CollUtil.newArrayList("mon","Tue","Wed"));
        map.put("y",CollUtil.newArrayList(120,130,110));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members(){
        List<User> list=userService.list();
        int q1=0;
        int q2=0;
        int q3=0;
        int q4=0;
        for(User user:list){
            Date createTime=user.getCreateTime();
            //QUARTER() 函数返回给定日期值（从 1 到 4 的数字）的一年中的季度。
            //1-3 月返回 1
            //4 月至 6 月返回 2
            //7-9 月返回 3
            //10 月至 12 月返回 4
            Quarter quarter= DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1:q1+=1;break;
                case Q2:q2+=1;break;
                case Q3:q3+=1;break;
                case Q4:q4+=1;break;
                default:break;
            }

        }
        return Result.success(CollUtil.newArrayList(q1,q2,q3,q4));
    }



}
