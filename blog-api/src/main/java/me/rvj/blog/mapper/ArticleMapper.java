package me.rvj.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.rvj.blog.entity.Article;
import me.rvj.blog.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 文章列表
     *
     * @param page
     * @return IPage<Article>
     * @author Rv_Jiang
     * @date 2022/5/24 23:45
     */
    IPage<ArticleVo> listArticle(IPage<ArticleVo> page);

    /**
     * 条件文章列表
     *
     * @param page
     * @return IPage<ArticleVo>
     * @author Rv_Jiang
     * @date 2022/5/26 9:44
     */
    IPage<ArticleVo> listArticleByCondition(IPage<ArticleVo> page,
                                            Long categoryId,
                                            Long[] tagId,
                                            Long upperLimitTime,
                                            Long lowerLimitTime,
                                            String search
                                            );

    /**
     * 文章详细内容
     *
     * @param id
     * @return ArticleDetailVo
     * @author Rv_Jiang
     * @date 2022/5/25 17:27
     */
    ArticleVo articleDetail(Long id);



}
