package com.lslr.demo.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lslr.demo.common.Result;
import com.lslr.demo.entity.Article;
import com.lslr.demo.entity.Project;
import com.lslr.demo.service.ArticleService;
import com.lslr.demo.service.ProjectService;
import com.lslr.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public static final String ARTICLE_KEY="ARTICLE_FRONT_ALL";


    //查询所有
    @GetMapping("/")
    public Result findAll() {
        //（1）从缓存获取数据
        String jsonStr= stringRedisTemplate.opsForValue().get(ARTICLE_KEY);
        if(StrUtil.isBlank(jsonStr)){          //（2）取出来的json是空的
            List<Article> articles=articleService.list();//（3）从数据库有取出数据
            //（4）缓存到redis
            stringRedisTemplate.opsForValue().set(ARTICLE_KEY,JSONUtil.toJsonStr(articles));
        }else{
            //（5）如果有数据，从redis缓存中获取数据

            //转换出错就忽略掉
            List<Article> articles=JSONUtil.toBean(jsonStr, new TypeReference<List<Article>>() {
            },true);
        }

        return Result.success();
    }

    ////新增或者更新
    @PostMapping
    public Result save(@RequestBody Article article) {//@RequestBody数据映射成对象
        if(article.getId()==null){
            article.setTime(DateUtil.now());  //创建的时间
            article.setUser(TokenUtils.getCurrentUser().getName());

        }
         articleService.saveOrUpdate(article);
         return Result.success();
    }
    //删除

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
         articleService.removeById(id);
         return Result.success();
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return Result.success(articleService.getById(id));
    }

    //分页查询  Mybatis-plus的方式查询
    //分页查询  Mybatis-plus的方式查询
    @GetMapping("/page1")
    @Transactional
    public IPage<Article> findPage2(@RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize,
                                    @RequestParam(defaultValue = "") String name) {
        IPage<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        return articleService.page(page, queryWrapper);


    }
}
