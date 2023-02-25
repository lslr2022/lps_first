package com.lslr.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lslr.demo.entity.Project;
import com.lslr.demo.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ProjectService extends ServiceImpl<ProjectMapper, Project> {
}
