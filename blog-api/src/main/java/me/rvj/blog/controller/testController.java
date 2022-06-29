package me.rvj.blog.controller;

import com.alibaba.fastjson.JSON;
import me.rvj.blog.entity.SysUser;
import me.rvj.blog.mapper.ArticleMapper;
import me.rvj.blog.mapper.SysUserMapper;
import me.rvj.blog.service.ArticleService;
import me.rvj.blog.vo.Result;
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
    @Autowired
    SysUserMapper sysUserMapper;

    @GetMapping("")
    public Result test(){
        System.out.println("Hello World");
        return null;
    }
}
