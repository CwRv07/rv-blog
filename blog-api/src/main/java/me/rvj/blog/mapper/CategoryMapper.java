package me.rvj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.rvj.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/5 17:57
 */

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
