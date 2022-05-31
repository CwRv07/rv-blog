package me.rvj.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.entity.Article;
import me.rvj.blog.mapper.ArticleMapper;
import me.rvj.blog.vo.ArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: rv-blog
 * @description: 定时任务
 * @author: Rv_Jiang
 * @date: 2022/5/30 16:16
 */

@Component
@EnableScheduling
@Slf4j
public class SetTimeOutService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 0 4 * * ?")
    public void saticScheduleTask(){


        log.info(" [定时任务] 更新文章阅读量："+System.currentTimeMillis());
        List<Article> articleList = articleMapper.selectList(null);
        for(Article article:articleList){
            Integer viewCount = Integer.valueOf((String)stringRedisTemplate.opsForHash().get(ThreadService.VIEW_COUNT, String.valueOf(article.getId())));
            if(!article.getViewCounts().equals(viewCount)){
                article.setViewCounts(viewCount);
                articleMapper.updateById(article);
            }
        }
    }
}
