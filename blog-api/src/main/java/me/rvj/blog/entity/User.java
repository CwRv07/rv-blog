package me.rvj.blog.entity;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: 用户类
 * @author: Rv_Jiang
 * @date: 2022/6/2 15:48
 */

@Data
public class User {
    private Long id;
    private String nickname;
    private String email;
    private String website;
}
