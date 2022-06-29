package me.rvj.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.service.CommentService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.CommentParams;
import me.rvj.blog.vo.params.UserParams;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
     *
     * @param commentParams
     * @param result
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/6/2 16:48
     */
    @PostMapping("insert")
    public Result insertComment(@Valid @RequestBody CommentParams commentParams, BindingResult result) {
        if (result.hasErrors()) {
            log.error("insertComment 参数错误");
            for (ObjectError error : result.getAllErrors()) {
                log.error(error.toString());
            }
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return commentService.insertComment(commentParams);
    }

    @GetMapping("listComment")
    public Result listComment(CommentParams commentParams) {
        if (commentParams.getPage() == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return commentService.listComment(commentParams.getPage(), commentParams.getPageSize());
    }

    @GetMapping("listCommentByStatus")
    public Result listCommentByStatus(CommentParams commentParams) {
        if (commentParams.getPage() == null || commentParams.getStatus() == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return commentService.listCommentByStatus(commentParams.getPage(), commentParams.getPageSize(), commentParams.getStatus());
    }

    @DeleteMapping("deleteComment/{commentId}")
    public Result deleteComment(@PathVariable("commentId") Long commentId) {
        if (commentId == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return commentService.deleteComment(commentId);
    }

    @PutMapping("updateComment")
    public Result updateComment(@RequestBody CommentParams commentParams) {
        if (ObjectUtils.anyNull(commentParams.getId(), commentParams.getPraise(), commentParams.getStatus())) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(commentParams.getContent())) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        if (commentParams.getStatus() != 1 && commentParams.getStatus() != 0) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return commentService.updateComment(commentParams.getId(), commentParams.getContent(), commentParams.getPraise(), commentParams.getStatus());
    }
}
