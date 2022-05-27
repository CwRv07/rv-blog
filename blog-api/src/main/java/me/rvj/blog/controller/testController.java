package me.rvj.blog.controller;

import com.alibaba.fastjson.JSON;
import me.rvj.blog.mapper.ArticleMapper;
import me.rvj.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/22 17:17
 */
@RestController
@RequestMapping("helloWorld")
public class testController {
    @Autowired
    ArticleService articleService;

//    @GetMapping("/{id}")
//    public Object test(@PathVariable(value="id") Long id){
//        return JSON.toJSON(articleService.findArticleById(id));
//    }
}
