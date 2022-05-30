package me.rvj.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.rvj.blog.entity.Article;
import me.rvj.blog.mapper.ArticleMapper;
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

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @PostConstruct
    /**
     * 初始化文章观看量
     * @param
     * @return void
     * @author Rv_Jiang
     * @date 2022/5/30 11:19
     */
    public void initViewCount() {
        List<Article> articleList = articleMapper.selectList(new QueryWrapper<>());
        for (Article article : articleList) {
            String viewCountStr = (String) stringRedisTemplate.opsForHash().get(
                    VIEW_COUNT,
                    String.valueOf(article.getId())
            );
            if (viewCountStr == null) {
                stringRedisTemplate.opsForHash().put(
                        VIEW_COUNT,
                        String.valueOf(article.getId()),
                        String.valueOf(article.getViewCounts())
                );
            }
        }
    }

    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleVo article) {
        stringRedisTemplate.opsForHash().increment(VIEW_COUNT, String.valueOf(article.getId()), 1);
    }

}
