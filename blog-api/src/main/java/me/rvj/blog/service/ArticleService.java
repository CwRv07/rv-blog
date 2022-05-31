package me.rvj.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import me.rvj.blog.entity.Article;
import me.rvj.blog.vo.PageParams;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.ArticleParams;
import org.springframework.stereotype.Service;

/**
 * @program: rv-blog
 * @description: Article服务层
 * @author: Rv_Jiang
 * @date: 2022/5/24 22:40
 */

public interface ArticleService {

    /**
     * 文章列表
     * @param pageParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/25 21:18
     */
    Result listArticle(PageParams pageParams);

    /**
     * 条件文章列表
     * @param pageParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/26 9:57
     */
    Result listArticleByCondition(PageParams pageParams);

    /**
     * 文章详情
     * @param id
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/25 21:25
     */
    Result articleDetail(Long id);

    /**
     * 上传文章
     * @param articleParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/26 18:20
     */
    Result uploadArticle(ArticleParams articleParams);

    /**
     * 更新文章
     * @param articleParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/31 10:05
     */
    Result updateArticle(ArticleParams articleParams);

    /**
     * 删除文章
     * @param articleId
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/27 21:23
     */
    Result deleteArticle(Long articleId);
}
