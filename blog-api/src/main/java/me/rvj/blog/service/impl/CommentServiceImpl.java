package me.rvj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.entity.Comment;
import me.rvj.blog.entity.User;
import me.rvj.blog.mapper.CommentMapper;
import me.rvj.blog.mapper.UserMapper;
import me.rvj.blog.service.CommentService;
import me.rvj.blog.service.ThreadService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.CommentParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/2 16:05
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ThreadService threadService;

    @Override
    public Result insertComment(CommentParams commentParams) {
        int insertNumber;

//        获取用户ID
        LambdaQueryWrapper<User> ulqw = new LambdaQueryWrapper();
        ulqw.eq(User::getEmail,commentParams.getEmail());
        User user = userMapper.selectOne(ulqw);

        if(ObjectUtils.isEmpty(user)){
            /* 新用户 */
            user=new User();
            user.setNickname(commentParams.getNickname());
            user.setEmail(commentParams.getEmail());
            user.setWebsite(commentParams.getWebsite());
            insertNumber= userMapper.insert(user);
            if(insertNumber==0){
                log.error("[评论新增操作] user新增失败",user);
                return Result.fail(ErrorCode.SERVER_BUSY);
            }
        }else{
            /* 旧用户 */
            boolean needUpdate=false;
            if(!StringUtils.equals(user.getNickname(),commentParams.getNickname())){
                /* 判断昵称是否需要更新 */
                user.setNickname(commentParams.getNickname());
                needUpdate=true;
            }
            if (StringUtils.isNotBlank(user.getEmail()) && !StringUtils.equals(user.getWebsite(), commentParams.getWebsite())) {
                /* 判断站点是否需要更新 */
                user.setWebsite(commentParams.getWebsite());
                needUpdate=true;
            }
            if(needUpdate){
                /* 暂且直接更新，未进行异步更新 */
                userMapper.updateById(user);
            }
        }

//        新增评论
        Comment comment = new Comment();
        comment.setContent(commentParams.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        comment.setArticleId(commentParams.getArticleId());
        comment.setAuthorId(user.getId());
        comment.setParentId(commentParams.getParentId());
        comment.setToUid(commentParams.getToUid());
        comment.setAncestorId(commentParams.getAncestorId());
        insertNumber = commentMapper.insert(comment);
        if(insertNumber==0) {
            log.error("[评论新增操作] comment新增操作失败", comment);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }
        threadService.updateArticleCommentCount(commentParams.getArticleId());
        return Result.success(comment);
    }

    @Override
    public Result listComment(Long page, Long pageSize) {
        Page<Comment> commentPage = new Page<>(page, pageSize);
        Page<Comment> commentList = commentMapper.selectPage(commentPage, null);

        return Result.success(commentList);
    }

    @Override
    public Result listCommentByStatus(Long page, Long pageSize, Integer status) {
        Page<Comment> commentPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Comment> commentLQW = new LambdaQueryWrapper<>();

        if (status != null) {
            commentLQW.like(Comment::getStatus, status);
        }
        Page<Comment> commentList = commentMapper.selectPage(commentPage, commentLQW);

        return Result.success(commentList);
    }

    @Override
    public Result deleteComment(Long id) {
        LambdaQueryWrapper<Comment> commentLQW = new LambdaQueryWrapper<>();

//        检测评论是否存在
        commentLQW.eq(Comment::getId, id);
        Comment comment = commentMapper.selectOne(commentLQW);
        if(org.apache.commons.lang3.ObjectUtils.isEmpty(comment)){
            return Result.fail(ErrorCode.COMMENT_NOT_EXIXT);
        }

        int delete = commentMapper.deleteById(id);
        if(delete!=0){
            return Result.success(id);
        }else{
            return Result.fail(ErrorCode.SERVER_BUSY);
        }
    }

    @Override
    public Result updateComment(Long id, String content, Integer praise, Integer status) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent(content);
        comment.setPraise(praise);
        comment.setStatus(status);

        if(commentMapper.updateById(comment)==0){
            /* 更新失败 */
            log.error("[tab更新操作] tag更新操作失败", comment);
            return Result.fail(ErrorCode.COMMENT_NOT_EXIXT);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("id", comment.getId());
        return Result.success(result);

    }


}
