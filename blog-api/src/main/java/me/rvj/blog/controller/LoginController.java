package me.rvj.blog.controller;

import me.rvj.blog.service.LoginService;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: rv-blog
 * @description: LoginController
 * @author: Rv_Jiang
 * @date: 2022/5/26 12:17
 */

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams){
        return loginService.login(loginParams);
    }
}
