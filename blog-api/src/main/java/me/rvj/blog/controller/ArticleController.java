package me.rvj.blog.controller;

import me.rvj.blog.service.ArticleService;
import me.rvj.blog.vo.PageParams;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.ArticleParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: rv-blog
 * @description: Article业务层
 * @author: Rv_Jiang
 * @date: 2022/5/24 23:10
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    
    @GetMapping("listArticle")
    /**
     * 文章列表Controller
     * @param 
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/25 21:19
     */
    public Result listArticle(){
        return articleService.listArticle(new PageParams());
    }

    @GetMapping("listArticleByCondition")
    /**
     * 条件文章列表Controller
     * @param 
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/27 20:35
     */
    public Result listArticleByCondition(){
        Long[] tagId = new Long[]{5L};
        return articleService.listArticleByCondition(new PageParams(1,10,null,null,123123L,null));
    }

    @GetMapping("detail/{id}")
    /**
     * 文章详情Controller
     * @param id
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/25 21:48
     */
    public Result articleDetail(@PathVariable Long id){return articleService.articleDetail(id);}
    
    @PostMapping("upload")
    /**
     * 上传文件
     * @param articleParams
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/27 20:40
     */
    public Result uploadArticle(@RequestBody ArticleParams articleParams){
        return articleService.uploadArticle(articleParams);
    }

    @DeleteMapping("delete/{articleId}")
    public Result deleteArticle(@PathVariable Long articleId){
        return articleService.deleteArticle(articleId);
    }
}
