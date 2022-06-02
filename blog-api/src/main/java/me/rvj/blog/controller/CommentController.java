package me.rvj.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.service.CommentService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/2 16:37
 */
@RestController
@RequestMapping("comment")
@Slf4j
public class CommentController {

    @Autowired
    CommentService commentService;


    /**
     * 新增评论
     * @param commentParams
 * @param result
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/6/2 16:48
     */
    @PostMapping("insert")
    public Result insertComment(@Valid @RequestBody CommentParams commentParams, BindingResult result){
        if(result.hasErrors()){
            log.error("insertComment 参数错误");
            for (ObjectError error : result.getAllErrors()) {
                log.error(error.toString());
            }
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return commentService.insertComment(commentParams);
    }
}
