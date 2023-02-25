package com.lslr.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.common.Constants;
import com.lslr.demo.common.Result;
import com.lslr.demo.controller.dto.UserDTO;
import com.lslr.demo.entity.User;
import com.lslr.demo.mapper.UserMapper;
import com.lslr.demo.service.UserService;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")

public class UserController {

    /*@Autowired
    private UserMapper userMapper;*/

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        String username=userDTO.getUsername();
        String password=userDTO.getPassword();
        if(StrUtil.isBlank(username) ||StrUtil.isBlank(password)){
            return Result.error(Constants.CODE_400,"参数错误");
        }
        UserDTO dto= userService.login(userDTO);
        return  Result.success(dto);
    }


    ////新增或者更新
    @PostMapping
    public boolean save(@RequestBody User user){  //@RequestBody数据映射成对象
        return userService.saveUser(user);
    }
    //查询所有
    @GetMapping("/")
    public List<User> findAll(){

        return userService.list();
    }
    /*@GetMapping("/user")
    public List<User> findAll1(){
        List<User> all1=userMapper.findAll1();
        return all1;
    }*/
    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }


    //根据用户用户名获取数据库这个用户所有属性
    @GetMapping("/username/{username}")
    public Result findUsername(@PathVariable String username){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //where username=#{username}
        queryWrapper.eq("username",username);
        return Result.success(userService.getOne(queryWrapper));
    }

    //修改密码
    @PostMapping("/password")
            public Result password(@RequestBody UserDTO userDTO){
        userService.updatePassword(userDTO);
        return Result.success();

    }



    //分页查询
    //@RequestParam 注解接收 ？pageNum=1&pageSize=10
    //limit第一个参数=（pageNum -1)*pageSize
    //第二个参数pageSize
   /* @GetMapping("/page1")
    public Map<String,Object>  findPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam String username,@RequestParam String nickname){
        pageNum=(pageNum -1)*pageSize;
        //username="%" +username + "%";
       List<User> data= userMapper.selectPage(pageNum,pageSize,username,nickname);
       Integer total= userMapper.selectTotal(username,nickname);
       Map<String,Object> res=new HashMap<>();
       res.put("data",data);
       res.put("total",total);
        return res;
    }*/
    //分页查询  Mybatis-plus的方式查询
    @GetMapping("/page1")
    public IPage<User>  findPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize,
                                 @RequestParam(defaultValue = "") String username,
                                 @RequestParam (defaultValue = "")String nickname,
                                 @RequestParam (defaultValue = "")String address){
        IPage<User>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        if(!"".equals(username)){
            queryWrapper.like("username",username);
        }
        if(!"".equals(nickname)){
            queryWrapper.like("nickname",nickname);
        }
        if(!"".equals(address)){
            queryWrapper.like("address",address);
        }

       return userService.page(page,queryWrapper);


    }


    //导入和导出功能

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //查询
        List<User> list=userService.list();
        //通过工具类创建writer 写出磁盘路径
        // ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath+"用户信息.xlsx");
        //内存操作，写出到浏览器
        ExcelWriter writer=ExcelUtil.getWriter(true);
        writer.addHeaderAlias("id","账号");
        writer.addHeaderAlias("username","用户名");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("nickname","昵称");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","电话");
        writer.addHeaderAlias("address","地址");
        writer.addHeaderAlias("avatar","头像");

        //写出list对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName= URLEncoder.encode("用户信息","UTF-8");
        response.setHeader("Content-Disposition","attachment;filename="+fileName+".xlsx");

        ServletOutputStream out=response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();

    }

    //导入
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream=file.getInputStream();
        ExcelReader reader =ExcelUtil.getReader(inputStream);
        List<User> list =reader.readAll(User.class);
        userService.saveBatch(list);
        return true;
    }

}
