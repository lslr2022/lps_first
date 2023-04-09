package com.lslr.demo.controller;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.common.Result;
import com.lslr.demo.entity.Files;
import com.lslr.demo.entity.User;
import com.lslr.demo.mapper.FileMapper;
import com.lslr.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

//文件上传相关接口
@RestController
@RequestMapping("/file")
public class FileController {


    @Value("${files.upload.path}")
    private String fileUploadPath;
    @Resource
    private FileMapper fileMapper;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        //名称
       String originalFilename= file.getOriginalFilename();
       //类型
      String type=FileUtil.extName(originalFilename);
      //大小
        long size= file.getSize();
        //定义一个文件标识位，调用HuTool工具类生成唯一id
        String uuid= IdUtil.fastSimpleUUID();
        //数据库url
        String fileUUID = uuid+StrUtil.DOT+type;

        File uploadFile =new File(fileUploadPath+fileUUID);
        //判断文件是否存在
       // File uploadParentFile=new File(fileUploadPath);
        File parentFile=uploadFile.getParentFile();
        if(!parentFile.exists()){
            //不存在则创建
            parentFile.mkdirs();
        }
        //获取文件的url
        String url;
        //上传文件到磁盘
       file.transferTo(uploadFile);
       //获取文件的md5
         String md5=SecureUtil.md5(uploadFile);
       //从数据库查询是否存在相同记录
        Files dbFiles= getFileByMd5(md5);
        if(dbFiles!=null){
            url=dbFiles.getUrl();
            //删除刚上传的重复的文件
            uploadFile.delete();
        }else {
            url="http://localhost:9090/file/"+fileUUID;
        }
        //转存到数据库
        Files saveFile=new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);
        return url;


    }
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException{
        //根据文件标识码获取文件
        File uploadFile =new File(fileUploadPath + fileUUID);
        //设置输出流的格式
          ServletOutputStream os= response.getOutputStream();
          //写出
        response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

    }
    //通过文件md5查询文件
    private Files getFileByMd5(String md5){
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("md5",md5);

         List<Files> filesList= fileMapper.selectList(queryWrapper);
         return filesList.size()  ==  0  ?null:filesList.get(0);
    }


    @Autowired
    private FileService fileService;

    /*   //第一种删除，对应的是File1  105行
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return fileService.removeById(id);
    }*/

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Files files=fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        return Result.success();
    }
    //新增或者更新
    @PostMapping("/update")
    public Result save(@RequestBody Files files){  //@RequestBody数据映射成对象
        return Result.success(fileMapper.updateById(files));
    }




    //分页查询  Mybatis-plus的方式查询

    @GetMapping("/page1")
    public IPage<Files> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String name){
        IPage<Files>  page=new Page<>(pageNum,pageSize);
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();

        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }

       return fileService.page(page,queryWrapper);


    }









}
