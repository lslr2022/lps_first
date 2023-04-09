package com.lslr.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.common.Result;
import com.lslr.demo.entity.Comment;
import com.lslr.demo.entity.User;
import com.lslr.demo.mapper.CommentMapper;
import com.lslr.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    //新增或者更新
    @PostMapping
    public boolean save(@RequestBody Comment comment){  //@RequestBody数据映射成对象
        return commentService.save(comment);
    }

    //查询所有
    @GetMapping("/")
    public Result findAll(){
        return Result.success(commentService.list());
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
         commentService.removeById(id);
         return Result.success();
    }

    @GetMapping("{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(commentService.getById(id));
    }
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize){
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(commentService.page(new Page<>(pageNum,pageSize),queryWrapper));

    }




}
