package me.rvj.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/21 16:05
 */

@Data
public class SysUser {

    private Long id;

    private String account;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private Long lastLogin;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
