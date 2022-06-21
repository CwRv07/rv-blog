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

    /**
     * 类别总数
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/21 10:26
     */
    public Result countCategory();
}
