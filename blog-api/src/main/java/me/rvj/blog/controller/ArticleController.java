package me.rvj.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.service.ArticleService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.params.PageParams;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.ArticleParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: rv-blog
 * @description: Article业务层
 * @author: Rv_Jiang
 * @date: 2022/5/24 23:10
 */
@RestController
@RequestMapping("articles")
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService articleService;



    /**
     * 文章列表Controller
     * @param
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/25 21:19
     */
    @GetMapping("listArticle")
    public Result listArticle(PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * 条件文章列表Controller
     * @param
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/27 20:35
     */
    @GetMapping("listArticleByCondition")
    public Result listArticleByCondition(PageParams pageParams){
        if(pageParams.getPageSize()==null){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return articleService.listArticleByCondition(pageParams);
    }


    /**
     * 文章详情Controller
     * @param articleId
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/25 21:48
     */
    @GetMapping("detail")
    public Result articleDetail(@RequestParam Long articleId){
        if(articleId==null){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return articleService.articleDetail(articleId);
    }



    /**
     * 上传文件
     * @param articleParams
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/27 20:40
     */
    @PostMapping("upload")
    public Result uploadArticle(@Valid @RequestBody ArticleParams articleParams, BindingResult result){
        if(result.hasErrors()){
            log.error("uploadArticleController 参数错误");
            for (ObjectError error : result.getAllErrors()) {
                log.error(error.toString());
            }
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return articleService.uploadArticle(articleParams);
    }


    /**
     * 更新文章
     * @param articleParams
     * @param result
     * @return me.rvj.blog.vo.Result
     * @author Rv_Jiang
     * @date 2022/5/31 19:44
     */
    @PutMapping("update")
    public Result updateArticle(@Valid @RequestBody ArticleParams articleParams,BindingResult result){
        if(result.hasErrors()){
            log.error("updateArticleController 参数错误");
            for (ObjectError error : result.getAllErrors()) {
                log.error(error.toString());
            }
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return articleService.updateArticle(articleParams);
    }


    /**
     * 删除文章
     * @param articleId
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/31 19:44
     */
    @DeleteMapping("delete/{articleId}")
    public Result deleteArticle(@PathVariable("articleId") Long articleId){
        if(articleId==null){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return articleService.deleteArticle(articleId);
    }
}
