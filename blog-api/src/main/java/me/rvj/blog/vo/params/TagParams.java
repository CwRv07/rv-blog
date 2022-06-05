package me.rvj.blog.vo.params;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: 标签Vo
 * @author: Rv_Jiang
 * @date: 2022/6/5 14:12
 */

@Data
public class TagParams {

    private Integer page = 1;

    private Integer pageSize = 10;

    private Long id;

    private String avatar;

    private String tagName;

    private Long categoryId;
}
