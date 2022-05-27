package me.rvj.blog.service;


import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.LoginParams;
import org.springframework.stereotype.Service;


public interface LoginService {

    /**
     * 登录
     * @param loginParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/26 12:23
     */
    public Result login(LoginParams loginParams);


    /**
     * 注册
     * @param loginParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/26 12:23
     */
    public Result register(LoginParams loginParams);

    /**
     * 检测Token
     * @param token
     * @return userId
     * @author Rv_Jiang
     * @date 2022/5/26 19:09
     */
    public Long checkToken(String token);
}
