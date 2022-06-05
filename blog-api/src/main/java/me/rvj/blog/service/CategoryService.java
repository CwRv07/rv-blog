package me.rvj.blog.service;


import me.rvj.blog.vo.Result;
import org.springframework.stereotype.Service;


public interface CategoryService {

    /**
     * 类别列表
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/5 18:03
     */
    public Result listCategory();
}
