package me.rvj.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
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
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
@Slf4j
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

//        读取缓存
        List<ArticleVo> records = articlePage.getRecords();
        int length=records.size();
        for(int i=0;i<length;i++){
            String viewCounts=getViewCounts(records.get(i).getId());
            if(StringUtils.isNotBlank(viewCounts)){
                records.get(i).setViewCounts(Integer.valueOf(viewCounts));
            }
        }
        articlePage.setRecords(records);

        return Result.success(articlePage);
    }

    @Override
    public Result listArticleByCondition(PageParams pageParams) {
//        初始化参数
        Integer pageNumber = pageParams.getPage();
        Long categoryId = pageParams.getCategoryId();
        Long[] tagId = pageParams.getTagId();
        Long upperLimitTime = pageParams.getUpperLimitTime();
        Long lowerLimitTime = pageParams.getLowerLimitTime();
        String search=StringUtils.trim(pageParams.getSearch());

        IPage<ArticleVo> page = new Page<>(pageNumber, pageParams.getPageSize());
        IPage<ArticleVo> articleVoPage = articleMapper.listArticleByCondition(page, categoryId, tagId, upperLimitTime, lowerLimitTime,search);

        //        读取缓存
        List<ArticleVo> records = articleVoPage.getRecords();
        int length=records.size();
        for(int i=0;i<length;i++){
            String viewCounts=getViewCounts(records.get(i).getId());
            if(StringUtils.isNotBlank(viewCounts)){
                records.get(i).setViewCounts(Integer.valueOf(viewCounts));
            }
        }
        articleVoPage.setRecords(records);

        return Result.success(articleVoPage);
    }


    @Override
    public Result articleDetail(Long id) {

        ArticleVo detail = articleMapper.articleDetail(id);
        /*更新阅读量*/
        threadService.updateArticleViewCount(detail);
        String viewCount = getViewCounts(detail.getId());
        if (viewCount != null) {
            detail.setViewCounts(Integer.parseInt(viewCount));
        }

        return Result.success(detail);
    }

    @Override
    public Result uploadArticle(ArticleParams articleParams) {
        /* 文章参数id需为空 */
        if (articleParams.getId() != null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
//        初始化参数
        int insertNumber;
        Long authorId = Long.valueOf(UserThreadLocal.get(UserThreadLocal.KEY_USER_ID));
        Article article = new Article();

        article.setCategoryId(articleParams.getCategoryId());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());


//        文章信息
        article.setAuthorId(authorId);
        article.setCommentCounts(0);
        article.setViewCounts(0);
        article.setWeight(0);
        article.setCreateDate(System.currentTimeMillis());
        insertNumber = articleMapper.insert(article);
        if (insertNumber == 0) {
            log.error("[文章新增操作] article添加失败", article);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }


//        文章内容
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParams.getContent());

        /* 新增文章内容 */
        articleBody.setArticleId(article.getId());
        articleBodyMapper.insert(articleBody);
        if (insertNumber == 0) {
            log.error("[文章新增操作] articleBody添加失败", article, articleBody);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }

        /* 更新article表内body_id字段 */
        article.setBodyId(articleBody.getId());
        insertNumber = articleMapper.updateById(article);
        if (insertNumber == 0) {
            log.error("[文章新增操作] article 更新失败", article, articleBody);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }

//        tag列表
        if (ArrayUtils.isNotEmpty(articleParams.getTags())) {
            /* 新增新articleTag */
            Long[] tags = articleParams.getTags();
            for (long tagId : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tagId);
                articleTagMapper.insert(articleTag);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("articleId", article.getId());
        return Result.success(result);
    }

    @Override
    public Result updateArticle(ArticleParams articleParams) {
        /* 文章id不为空 */
        if (articleParams.getId() == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
//        初始化参数
        int updateNumber;
        Long authorId = Long.valueOf(UserThreadLocal.get(UserThreadLocal.KEY_USER_ID));
        Article article = new Article();

        article.setId(articleParams.getId());
        article.setCategoryId(articleParams.getCategoryId());
        article.setSummary(articleParams.getSummary());
        article.setTitle(articleParams.getTitle());

//        文章信息
        updateNumber = articleMapper.updateById(article);
        if (updateNumber == 0) {
            return Result.fail(ErrorCode.ARTICLE_NOT_EXIST);
        }


//        文章内容
        ArticleBody articleBody = new ArticleBody();
        articleBody.setContent(articleParams.getContent());

        LambdaQueryWrapper<ArticleBody> ablqw = new LambdaQueryWrapper<>();
        ablqw.eq(ArticleBody::getArticleId, article.getId());
        updateNumber = articleBodyMapper.update(articleBody, ablqw);
        if (updateNumber == 0) {
            log.warn("[文章更新操作] articleBody更新失败", article, articleBody);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }

//        tag列表
        if (ArrayUtils.isNotEmpty(articleParams.getTags())) {
            /* 清空articleTag */
            LambdaQueryWrapper<ArticleTag> atlqw = new LambdaQueryWrapper();
            atlqw.eq(ArticleTag::getArticleId, article.getId());
            articleTagMapper.delete(atlqw);

            /* 新增新articleTag */
            Long[] tags = articleParams.getTags();
            for (long tagId : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tagId);
                articleTagMapper.insert(articleTag);
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("articleId", article.getId());
        return Result.success(result);

    }

    @Override
    public Result deleteArticle(Long articleId) {
        int deleteNumber;
        /* articleId不为空 */
        if (articleId == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }

        Article article = articleMapper.selectById(articleId);
        /* 确认删除文章存在 */
        if (ObjectUtils.isEmpty(article)) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }

        /* 删除articleBody */
        deleteNumber = articleBodyMapper.deleteById(article.getBodyId());
        if (deleteNumber == 0) {
            log.error("[文章删除操作] articleBody删除失败", article);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }

        /* 删除tag */
        LambdaQueryWrapper<ArticleTag> atlqw = new LambdaQueryWrapper<>();
        atlqw.eq(ArticleTag::getArticleId, articleId);
        articleTagMapper.delete(atlqw);

        /* 删除article */
        boolean isDelete = articleMapper.deleteById(articleId) != 0;
        if (!isDelete) {
            log.error("[文章删除操作] article删除失败", article);
            return Result.fail(ErrorCode.SERVER_BUSY);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("articleId", articleId);
        return Result.success(result);
    }

    /**
     * 获取缓存阅读量
     * @param articleId
     * @return String
     * @author Rv_Jiang
     * @date 2022/6/7 9:02
     */
    public String getViewCounts(long articleId){
       return (String) stringRedisTemplate.opsForHash().get("view_count", String.valueOf(articleId));
    }
}
