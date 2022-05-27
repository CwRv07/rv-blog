package me.rvj.blog.vo.params;

import lombok.Data;

/**
 * @program: rv-blog
 * @description: 登录参数Vo
 * @author: Rv_Jiang
 * @date: 2022/5/26 12:04
 */

@Data
public class LoginParams {

    private String account;

    private String password;

    private String nickname;

}
