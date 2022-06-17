package me.rvj.blog.controller;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.service.UserService;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.UserParams;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: rv-blog
 * @description: 游客用户业务层
 * @author: Rv_Jiang
 * @date: 2022/6/16 17:35
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/listUser")
    public Result listUser(UserParams userParams) {
        if (userParams.getPage() == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return userService.listUser(userParams.getPage(), userParams.getPageSize());
    }

    @GetMapping("listUserByCondition")
    public Result listUserByCondition(UserParams userParams) {
        if (userParams.getPage() == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }

        /* 去除两端空格 */
        if(StringUtils.isNotBlank(userParams.getNickname())){
            userParams.setNickname(userParams.getNickname().trim());
        }

        return userService.listUserByCondition(userParams.getPage(), userParams.getPageSize(), userParams.getId(), userParams.getNickname());
    }

    @PostMapping("insertUser")
    public Result insertUser(UserParams userParams) {
        if (ObjectUtils.anyNull(userParams.getNickname(),userParams.getEmail())|| StringUtils.isAnyBlank(userParams.getNickname(),userParams.getEmail())) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }

        /* 去除两端空格 */
        if(StringUtils.isNotBlank(userParams.getWebsite())){
            userParams.setWebsite(userParams.getWebsite().trim());
        }

        return userService.insertUser(userParams.getNickname().trim(), userParams.getEmail().trim(), userParams.getWebsite());
    }

    @DeleteMapping("deleteUser")
    public Result deleteUser(UserParams userParams){
        if(ObjectUtils.isEmpty(userParams.getId())){
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return userService.deleteUser(userParams.getId());
    }

}
