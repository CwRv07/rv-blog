package me.rvj.blog.vo.params;

import lombok.Data;

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

    private String content;

    private Long categoryId;

    private String summary;

    private Long[] tags;

    private String title;

    private String search;
}
