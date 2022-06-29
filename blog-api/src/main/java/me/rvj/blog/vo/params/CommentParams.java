package me.rvj.blog.vo.params;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/2 15:56
 */
@Data
public class CommentParams {

    private Long page;

    private Long pageSize=6L;

    private Integer status;

    private Long id;

    private Integer praise;

    @NotBlank(message = "昵称不可为空")
    private String nickname;

    @NotBlank(message = "邮箱不可为空")
    @Email
    private String email;

    private String website;

    @NotBlank(message = "评论内容不可为空")
    private String content;

    @NotNull(message = "文章ID不可为空")
    private Long articleId;

    @NotNull(message="父级评论ID不可为空")
    private Long parentId;

    @NotNull(message="回复评论的用户ID不可为空")
    private Long toUid;


    @NotNull(message="祖先评论ID不可为空")
    private Long ancestorId;
}
