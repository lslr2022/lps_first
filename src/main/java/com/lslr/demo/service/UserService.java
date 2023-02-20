package com.lslr.demo.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lslr.demo.common.Constants;
import com.lslr.demo.common.exception.ServiceException;
import com.lslr.demo.controller.dto.UserDTO;
import com.lslr.demo.entity.User;
import com.lslr.demo.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user) {
       /* if(user.getId()==null){
            return save(user);    //mybatis-plus提供的方法，插入数据
        }else{
            return updateById(user);
        }*/
        return saveOrUpdate(user);
    }
    private static final Log LOG =Log.get();

    //登录  queryWrapper是mybatis plus中实现查询的对象封装操作类
    public UserDTO login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try{
            one=getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one!=null){
            BeanUtil.copyProperties(one,userDTO,true);
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }

    }




/*
    @Autowired
    private UserMapper userMapper;
*/

    /*public int save(User user){
        if(user.getId()==null){
            return userMapper.insert(user);
        }else{
            return userMapper.update(user);
        }

    }*/
}
