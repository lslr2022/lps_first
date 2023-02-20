package com.lslr.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lslr.demo.controller.dto.UserDTO;
import com.lslr.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
// @Mapper
public interface UserMapper extends BaseMapper<User> {


  /*  //查询所有
    @Select("SELECT *from user1")
    List<User> findAll();

  *//*  @Select("Select *from user1")
    List<User> findAll1();*//*

    //插入数据
    @Insert("INSERT into user1(username,password,nickname,email,phone,address) VALUES (#{username},#{password},"+
            "#{nickname},#{email},#{phone},#{address})")

    int insert(User user);

    int update(User user);
    //删除数据
    @Delete("delete from user1 where id= #{id}")
    Integer deleteById(@Param("id") Integer id);

    //分页查询
    @Select("select * from user1 where username like  concat ('%',#{username},'%') && nickname like concat('%',#{nickname},'%')" +
            " limit #{pageNum},#{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize,String username,String nickname);  //where username like concat('%',#{username},'%' )

    @Select("select count(*) from user1 where username like  concat ('%',#{username},'%') && nickname like concat('%',#{nickname},'%')" )
    Integer selectTotal(String username,String nickname);*/
}