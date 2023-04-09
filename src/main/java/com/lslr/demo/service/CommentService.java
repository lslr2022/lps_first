package com.lslr.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lslr.demo.entity.Comment;
import com.lslr.demo.mapper.CommentMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {
}
