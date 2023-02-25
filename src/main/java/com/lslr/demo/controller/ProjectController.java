package com.lslr.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.entity.Project;
import com.lslr.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    //查询所有
    @GetMapping("/")
    public List<Project> findAll(){
        return projectService.list();
    }
    ////新增或者更新
    @PostMapping
    public boolean save(@RequestBody Project project){  //@RequestBody数据映射成对象
        return projectService.saveOrUpdate(project);
    }
    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return projectService.removeById(id);
    }
    //分页查询  Mybatis-plus的方式查询
    @GetMapping("/page1")
    public IPage<Project> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String projectname,
                                @RequestParam (defaultValue = "")String projecttype,
                                @RequestParam (defaultValue = "")String projectcondition){
        IPage<Project>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        if(!"".equals(projectname)){
            queryWrapper.like("projectname",projectname);
        }
        if(!"".equals(projecttype)){
            queryWrapper.like("projecttype",projecttype);
        }
        if(!"".equals(projectcondition)){
            queryWrapper.like("projectcondition",projectcondition);
        }

        return projectService.page(page,queryWrapper);


    }














}
