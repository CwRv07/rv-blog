package me.rvj.blog.vo;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: AuthorVo
 * @author: Rv_Jiang
 * @date: 2022/5/25 22:04
 */

@Data
public class UserVo {
    private Long id;

    private String nickname;

    private String avatar;

    private String website;

}
