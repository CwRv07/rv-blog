package me.rvj.blog.entity;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: 类别实体
 * @author: Rv_Jiang
 * @date: 2022/5/25 18:18
 */

@Data
public class Category {
    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}
