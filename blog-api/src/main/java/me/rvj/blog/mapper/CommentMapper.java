package me.rvj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import me.rvj.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
