package me.rvj.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.rvj.blog.entity.Article;
import me.rvj.blog.entity.SysUser;
import me.rvj.blog.mapper.ArticleMapper;
import me.rvj.blog.mapper.SysUserMapper;
import me.rvj.blog.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/30 11:09
 */
@Component
public class ThreadService {

    public static final String VIEW_COUNT = "view_count";
    public static final String COMMENT_COUNT = "comment_count";
    public static final String PREFIX_ARTICLE="article:";

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostConstruct
    /**
     * 初始化文章数据
     * @param
     * @return void
     * @author Rv_Jiang
     * @date 2022/5/30 11:19
     */
    public void initViewCountAndCommentCount() {
        List<Article> articleList = articleMapper.selectList(new QueryWrapper<>());
        for (Article article : articleList) {
//            阅读量
            String viewCountStr = (String) stringRedisTemplate.opsForHash().get(
                    VIEW_COUNT,
                    PREFIX_ARTICLE+article.getId()
            );
            if (viewCountStr == null) {
                stringRedisTemplate.opsForHash().put(
                        VIEW_COUNT,
                        PREFIX_ARTICLE+article.getId(),
                        String.valueOf(article.getViewCounts())
                );
            }
//            评论数
            String commentCountStr = (String) stringRedisTemplate.opsForHash().get(
                    COMMENT_COUNT,
                    PREFIX_ARTICLE+article.getId()
            );
            if (viewCountStr == null) {
                stringRedisTemplate.opsForHash().put(
                        COMMENT_COUNT,
                        PREFIX_ARTICLE+article.getId(),
                        String.valueOf(article.getCommentCounts())
                );
            }
        }
    }

    @Async("taskExecutor")
    public void updateArticleViewCount(Long articleId) {
        stringRedisTemplate.opsForHash().increment(VIEW_COUNT, PREFIX_ARTICLE+String.valueOf(articleId), 1);
    }

    @Async("taskExecutor")
    public void updateArticleCommentCount(Long articleId){
        stringRedisTemplate.opsForHash().increment(COMMENT_COUNT, PREFIX_ARTICLE+String.valueOf(articleId), 1);
    }

    @Async("taskExecutor")
    public void updateSysUser(SysUser sysUser,String token){
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUserMapper.updateById(sysUser);
    }

}
