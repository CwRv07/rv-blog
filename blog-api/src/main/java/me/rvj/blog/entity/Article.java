package me.rvj.blog.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/21 16:05
 */

@Data
public class Article {

//    全局公共常量
    public static final int Article_TOP = 1;
    public static final int Article_Common = 0;

//    属性
    private Long id;

    private String title;

    private String summary;

    private String avatar;

    private Integer commentCounts;

    private Integer viewCounts;

    @JsonIgnore
    private Long authorId;

    @JsonIgnore
    private Long bodyId;

    @JsonIgnore
    private Long categoryId;

    private Integer weight;

    private Long createDate;

}
