package com.lslr.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.common.Constants;
import com.lslr.demo.common.Result;
import com.lslr.demo.entity.Files;
import com.lslr.demo.entity.Project;
import com.lslr.demo.mapper.FileMapper;
import com.lslr.demo.mapper.ProjectMapper;
import com.lslr.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Resource
    private ProjectMapper projectMapper;
    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private FileController fileController;


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
    //审核
    @PostMapping("/{id}")
    public Result review(@PathVariable Integer id){
        Project project=projectMapper.selectById(id);
        project.setIsReview(true);
        projectMapper.updateById(project);
        return Result.success();
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file,@RequestParam(value = "id",defaultValue = "1") Integer id) throws IOException {
        String url=fileController.upload(file);
        Project project=projectMapper.selectById(id);
        project.setUrl(url);
        String filename=file.getOriginalFilename();
        project.setFileName(filename);
        projectMapper.updateById(project);
        return filename;

    }
    //通过文件md5查询文件
    private Files getFileByMd5(String md5){
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> filesList= fileMapper.selectList(queryWrapper);
        return filesList.size()  ==  0  ?null:filesList.get(0);
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
        //查询未通过的记录
        queryWrapper.eq("is_review",false);
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
    @GetMapping("/page11")
    public IPage<Project> findPage1(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String projectname,
                                   @RequestParam (defaultValue = "")String projecttype,
                                   @RequestParam (defaultValue = "")String projectcondition){
        IPage<Project>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        //查询已通过的记录
        queryWrapper.eq("is_review",true);
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
    //分页查询  Mybatis-plus的方式查询
    //与前端登录用户名字相匹配
    //查找全部项目
    @GetMapping("/page12")
    @Transactional
    public IPage<Project> findPage2(@RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize,
                                   @RequestParam(defaultValue = "") String projectname,
                                   @RequestParam (defaultValue = "")String projecttype,
                                   @RequestParam (defaultValue = "")String projectcondition,
                                   @RequestParam (defaultValue = "")String name){
        IPage<Project>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("projectmember",name);
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
    //分页查询  Mybatis-plus的方式查询
    //与前端登录用户名字相匹配
    //查找已通过项目
    @GetMapping("/page122")
    @Transactional
    public IPage<Project> findPage22(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String projectname,
                                    @RequestParam (defaultValue = "")String projecttype,
                                    @RequestParam (defaultValue = "")String projectcondition,
                                    @RequestParam (defaultValue = "")String name){
        IPage<Project>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<Project> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("projectmember",name);
        queryWrapper.eq("is_review",true);

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
