package com.lslr.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.common.Result;
import com.lslr.demo.entity.Project;
import com.lslr.demo.entity.StuProject;
import com.lslr.demo.entity.User;
import com.lslr.demo.service.StuProjectService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stuProject")
public class StuProjectController {
    @Autowired
    private StuProjectService stuProjectService;
    //查询所有
    @GetMapping("/")
    public List<StuProject> findAll(){
        return stuProjectService.list();
    }
/*    //查询所有匹配的项目名称,下拉框的设计
    @GetMapping("/projectname/{projectname}")
    public Result findProjectsByProjectName(@PathVariable String projectname){
        QueryWrapper<StuProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("projectname",projectname);
        List<StuProject> list=stuProjectService.list(queryWrapper);
        return Result.success(list);
    }*/
    //新增或者更新
    @PostMapping("/save")
    public boolean save(@RequestBody StuProject stuProject){  //@RequestBody数据映射成对象
        return stuProjectService.saveStuProject(stuProject);
    }
    //删除
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return stuProjectService.removeById(id);
    }


    //分页查询  Mybatis-plus的方式查询
    @GetMapping("/page2")
    public IPage<StuProject> findPage(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String projectname,
                                   @RequestParam (defaultValue = "")String projecttype,
                                   @RequestParam (defaultValue = "")String projectcondition){
        IPage<StuProject>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<StuProject> queryWrapper=new QueryWrapper<>();
        if(!"".equals(projectname)){
            queryWrapper.like("projectname",projectname);
        }
        if(!"".equals(projecttype)){
            queryWrapper.like("projecttype",projecttype);
        }
        if(!"".equals(projectcondition)){
            queryWrapper.like("projectcondition",projectcondition);
        }

        return stuProjectService.page(page,queryWrapper);


    }
}
