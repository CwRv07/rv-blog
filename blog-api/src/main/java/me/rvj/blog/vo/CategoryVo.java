package me.rvj.blog.vo;

import lombok.Data;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/5 14:26
 */

@Data
public class CategoryVo {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;

}
