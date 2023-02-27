package com.lslr.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lslr.demo.entity.Files;
import com.lslr.demo.mapper.FileMapper;
import org.springframework.stereotype.Service;

@Service
public class FileService extends ServiceImpl<FileMapper, Files> {
}
