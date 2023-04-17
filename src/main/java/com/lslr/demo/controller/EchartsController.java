package com.lslr.demo.controller;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.lslr.demo.common.Constants;
import com.lslr.demo.common.Result;
import com.lslr.demo.config.interceptor.AuthAccess;
import com.lslr.demo.entity.Article;
import com.lslr.demo.entity.Files;
import com.lslr.demo.entity.User;
import com.lslr.demo.mapper.FileMapper;
import com.lslr.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
    @Resource
    private FileMapper fileMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;



    //实验1
    @GetMapping("/column")
    public Result get() {
        Map<String, Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("mon", "Tue", "Wed"));
        map.put("y", CollUtil.newArrayList(120, 130, 110));
        return Result.success(map);
    }

    @GetMapping("/members")
    public Result members() {
        List<User> list = userService.list();
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        for (User user : list) {
            //获取时间
            Date createTime = user.getCreateTime();
            //QUARTER() 函数返回给定日期值（从 1 到 4 的数字）的一年中的季度。
            //1-3 月返回 1
            //4 月至 6 月返回 2
            //7-9 月返回 3
            //10 月至 12 月返回 4
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter) {
                case Q1:
                    q1 += 1;
                    break;
                case Q2:
                    q2 += 1;
                    break;
                case Q3:
                    q3 += 1;
                    break;
                case Q4:
                    q4 += 1;
                    break;
                default:
                    break;
            }

        }
        return Result.success(CollUtil.newArrayList(q1, q2, q3, q4));
    }
    @AuthAccess
    @GetMapping("/file/front/all")
    public Result frontAll(){
        //（1）从缓存获取数据
        String jsonStr= stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
        List<Files> files;
        if(StrUtil.isBlank(jsonStr)){          //（2）取出来的json是空的
          files=fileMapper.selectList(null);//（3）从数据库有取出数据
            //（4）缓存到redis
            stringRedisTemplate.opsForValue().set(Constants.FILES_KEY, JSONUtil.toJsonStr(files));
        }else{
            //（5）如果有数据，从redis缓存中获取数据
            //转换出错就忽略掉
            files=JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
            },true);
        }

        return Result.success(files);
    }

}

















