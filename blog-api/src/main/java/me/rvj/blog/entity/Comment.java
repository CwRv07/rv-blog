package me.rvj.blog.entity;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: 评论实体类
 * @author: Rv_Jiang
 * @date: 2022/5/25 22:19
 */
@Data
public class Comment {
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long ancestorId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer praise;

    private Integer status;
}
