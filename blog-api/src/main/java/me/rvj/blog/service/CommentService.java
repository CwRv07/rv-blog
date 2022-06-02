package me.rvj.blog.service;

import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.CommentParams;
import org.springframework.stereotype.Service;


public interface CommentService {

    /**
     * 新增评论
     * @param commentParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/2 15:57
     */
    public Result insertComment(CommentParams commentParams);
}
