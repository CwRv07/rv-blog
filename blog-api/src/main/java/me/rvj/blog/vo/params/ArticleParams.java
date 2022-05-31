package me.rvj.blog.vo.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: rv-blog
 * @description: ArticleParams
 * @author: Rv_Jiang
 * @date: 2022/5/26 18:20
 */
@Data
public class ArticleParams {

    private Long id;

    @NotBlank(message = "文章内容不可为空")
    private String content;

    @NotNull(message = "文章类别不可为空")
    private Long categoryId;

    @NotBlank(message = "文章概述不可为空")
    private String summary;

    @NotNull(message = "文章标签不可为空")
    private Long[] tags;

    @NotBlank(message = "文章标题不可为空")
    private String title;

    private String search;
}
