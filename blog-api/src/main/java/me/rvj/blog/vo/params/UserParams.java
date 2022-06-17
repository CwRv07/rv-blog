package me.rvj.blog.vo.params;

import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @program: rv-blog
 * @description: 登录参数Vo
 * @author: Rv_Jiang
 * @date: 2022/5/26 12:04
 */

@Data
public class UserParams {

    private Long page;
    private Long pageSize=10L;

    private Long id;

    private String nickname;

    @Email
    private String email;

    private String website;

}
