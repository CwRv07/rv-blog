package me.rvj.blog.vo.params;

import lombok.Data;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/6/16 18:52
 */
@Data
public class SysUserParams {
    String account;
    String password;
    String nickname;
}
