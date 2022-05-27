package me.rvj.blog.entity;

import lombok.Data;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/21 16:05
 */

@Data
public class Tag {

    private Long id;

    private String avatar;

    private String tagName;
}
