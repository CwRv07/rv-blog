package me.rvj.blog.controller;

import me.rvj.blog.service.CategoryService;
import me.rvj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/5 18:05
 */

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("listCategory")
    public Result listCategory(){
        return categoryService.listCategory();
    }

    @GetMapping("total")
    public Result countCategory(){
        return categoryService.countCategory();
    }
}
