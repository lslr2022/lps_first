package com.lslr.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lslr.demo.entity.Article;
import com.lslr.demo.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {
}
