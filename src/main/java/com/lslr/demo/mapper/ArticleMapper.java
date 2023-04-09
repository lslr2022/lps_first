package com.lslr.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lslr.demo.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
