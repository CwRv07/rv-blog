package me.rvj.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.rvj.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/2 15:49
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
