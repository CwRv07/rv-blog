package me.rvj.blog.service;


import me.rvj.blog.vo.Result;
import me.rvj.blog.vo.params.SysUserParams;
import me.rvj.blog.vo.params.UserParams;


public interface LoginService {

    /**
     * 登录
     * @param userParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/26 12:23
     */
    public Result login(SysUserParams userParams);


    /**
     * 注册
     * @param userParams
     * @return Result
     * @author Rv_Jiang
     * @date 2022/5/26 12:23
     */
    public Result register(SysUserParams userParams);

    /**
     * 检测Token
     * @param token
     * @return userId
     * @author Rv_Jiang
     * @date 2022/5/26 19:09
     */
    public Long checkToken(String token);
}
