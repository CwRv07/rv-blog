package me.rvj.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import me.rvj.blog.entity.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;
import java.util.Map;

/**
 * @program: rv-blog
 * @description: ArticleVo
 * @author: Rv_Jiang
 * @date: 2022/5/25 21:34
 */
@Data
public class ArticleVo {

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;

    private Long createDate;

    /**
     * 文章内容
     */
    @TableField(exist = false)
    private String content;

    /**
     * 作者信息
     */
    @TableField(exist = false)
    private UserVo author;

    /**
     * 类别
     */
    @TableField(exist = false)
    private Category category;

    /**
     * 评论
     */
    @TableField(exist = false)
    private List<CommentVo> commentList;

    /**
     * 标签列表
     */
    @TableField(exist = false)
    private List<Tag> tagList;

}
