package com.lslr.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lslr.demo.entity.StuProject;
import com.lslr.demo.entity.User;
import com.lslr.demo.mapper.StuProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuProjectService extends ServiceImpl<StuProjectMapper, StuProject> {
    public boolean saveStuProject(StuProject stuProject) {
       /* if(user.getId()==null){
            return save(user);    //mybatis-plus提供的方法，插入数据
        }else{
            return updateById(user);
        }*/
        return saveOrUpdate(stuProject);
    }

}
