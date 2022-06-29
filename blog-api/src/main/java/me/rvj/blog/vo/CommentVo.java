package me.rvj.blog.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * @program: rv-blog
 * @description: CommentVo
 * @author: Rv_Jiang
 * @date: 2022/5/25 22:50
 */
@Data
public class CommentVo {

    private Long id;

    @TableField(exist = false)
    private UserVo author;

    private String content;

    private Long ancestorId;

    private Long parentId;

    private String createDate;

    private Integer praise;

    @TableField(exist = false)
    private UserVo toUser;


}
