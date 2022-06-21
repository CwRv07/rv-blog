package me.rvj.blog.service.impl;

import me.rvj.blog.entity.Category;
import me.rvj.blog.mapper.CategoryMapper;
import me.rvj.blog.service.CategoryService;
import me.rvj.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: rv-blog
 * @description: CategoryServiceImpl
 * @author: Rv_Jiang
 * @date: 2022/6/5 18:00
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Result listCategory(){
        List<Category> list = categoryMapper.selectList(null);
        return Result.success(list);

    }

    @Override
    public Result countCategory() {
        Integer categoryNumber = categoryMapper.selectCount(null);
        return Result.success(categoryNumber);
    }
}
