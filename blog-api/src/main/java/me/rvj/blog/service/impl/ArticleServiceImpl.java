package me.rvj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.rvj.blog.entity.Article;
import me.rvj.blog.entity.ArticleBody;
import me.rvj.blog.entity.ArticleTag;
import me.rvj.blog.mapper.ArticleBodyMapper;
import me.rvj.blog.mapper.ArticleMapper;
import me.rvj.blog.mapper.ArticleTagMapper;
import me.rvj.blog.mapper.TagMapper;
import me.rvj.blog.service.ArticleService;
import me.rvj.blog.service.ThreadService;
import me.rvj.blog.util.UserThreadLocal;
import me.rvj.blog.vo.*;
import me.rvj.blog.vo.params.ArticleParams;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: rv-blog
 * @description: 服务层实现
 * @author: Rv_Jiang
 * @date: 2022/5/24 22:40
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result listArticle(PageParams pageParams) {
//        初始化参数
        IPage<ArticleVo> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());

        IPage<ArticleVo> articlePage = articleMapper.listArticle(page);

        return Result.success(articlePage.getRecords());
    }

    @Override
    public Result listArticleByCondition(PageParams pageParams) {

        IPage<ArticleVo> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        Long categoryId = pageParams.getCategoryId();
        Long[] tagId = pageParams.getTagId();
        Long upperLimitTime = pageParams.getUpperLimitTime();
        Long lowerLimitTime = pageParams.getLowerLimitTime();

        IPage<ArticleVo> articleVoPage = articleMapper.listArticleByCondition(page, categoryId, tagId, upperLimitTime, lowerLimitTime);

        return Result.success(articleVoPage.getRecords());
    }


    @Override
    public Result articleDetail(Long id) {

        ArticleVo detail = articleMapper.articleDetail(id);
        /*更新阅读量*/
        threadService.updateArticleViewCount(detail);
        String viewCount = (String) stringRedisTemplate.opsForHash().get("view_count", String.valueOf(detail.getId()));
        if (viewCount != null){
            detail.setViewCounts(Integer.parseInt(viewCount));
        }

        return Result.success(detail);
    }

    @Override
    public Result uploadArticle(ArticleParams articleParams) {
//        初始化参数
        Long authorId = UserThreadLocal.get();
//        Long authorId = 1L;
        Article article = new Article();

        article.setId(articleParams.getId());
        article.setCategoryId(articleParams.getCategoryId());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());

        /* 判断文章为新增还是更新 */
        boolean isUpdate = articleParams.getId() != null;

//        文章信息
        if (isUpdate) {
            /* 更新文章信息 */
            if (ObjectUtils.anyNotNull(article.getCategoryId(), article.getSummary(), article.getTitle())) {
                articleMapper.updateById(article);
            }
        } else {
            article.setAuthorId(authorId);
            article.setCommentCounts(0);
            article.setViewCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            articleMapper.insert(article);
        }

//        文章内容(需保证不为空)
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(StringUtils.defaultIfEmpty(articleParams.getContent(), ""));
        if (isUpdate) {
            /* 更新文章内容 */
            LambdaQueryWrapper<ArticleBody> lqw = new LambdaQueryWrapper<>();
            lqw.eq(ArticleBody::getArticleId, article.getId());
            articleBodyMapper.update(articleBody, lqw);

        } else {
            /* 新增文章内容 */
            articleBody.setArticleId(article.getId());
            articleBodyMapper.insert(articleBody);
            /* 更新article表内body_id字段 */
            article.setBodyId(articleBody.getId());
            articleMapper.updateById(article);
        }
//        tag列表
        if (articleParams.getTags() != null) {
            /* 清空articleTag */
            LambdaQueryWrapper<ArticleTag> lqw = new LambdaQueryWrapper();
            lqw.eq(ArticleTag::getArticleId, article.getId());
            articleTagMapper.delete(lqw);

            /* 新增新articleTag */
            lqw = new LambdaQueryWrapper<>();
            Long[] tags = articleParams.getTags();
            for (long tagId : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tagId);
                articleTagMapper.insert(articleTag);
            }
        }
        return Result.success(new HashMap().put("articleId", article.getId()));
    }

    @Override
    public Result deleteArticle(Long articleId) {
        boolean isDeleted = articleMapper.deleteById(articleId) != 0;
        return isDeleted ?
                Result.success(null) : Result.fail(ErrorCode.PARAMS_ERROR);

    }
}
