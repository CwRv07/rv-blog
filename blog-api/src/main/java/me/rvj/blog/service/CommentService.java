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

    /**
     * 评论列表
     * @param page
     * @param pageSize
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/29 16:25
     */
    public Result listComment(Long page, Long pageSize);

    /**
     * 评论列表
     * @param page
     * @param pageSize
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/29 16:25
     */
    public Result listCommentByStatus(Long page, Long pageSize,Integer status);

    /**
     * 删除评论
     * @param id
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/29 16:38
     */
    public Result deleteComment(Long id);

    /**
     * 更新评论
     * @param id
     * @param content
     * @param praise
     * @param status
     * @return Result
     * @author Rv_Jiang
     * @date 2022/6/29 21:50
     */
    public Result updateComment(Long id,String content,Integer praise,Integer status);
}
