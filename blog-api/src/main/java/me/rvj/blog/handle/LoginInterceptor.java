package me.rvj.blog.handle;

import lombok.extern.slf4j.Slf4j;
import me.rvj.blog.entity.SysUser;
import me.rvj.blog.service.LoginService;
import me.rvj.blog.util.JWTUtils;
import me.rvj.blog.util.UserThreadLocal;
import me.rvj.blog.vo.ErrorCode;
import me.rvj.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: rv-blog
 * @description: LoginInterceptor
 * @author: Rv_Jiang
 * @date: 2022/5/26 18:29
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)){
            //handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
            return true;
        }

//        日志记录
        String token = request.getHeader("Authorization");
        log.info("=================");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================");

        /* token为空 */
        if(StringUtils.isBlank(token)){
            Result result= Result.fail(ErrorCode.NO_LOGIN);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(result);
            return false;
        }

        /* token无效 */
        Long userId = loginService.checkToken(token);
        if(userId==null){
            Result result= Result.fail(ErrorCode.SESSION_TIME_OUT);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().println(result);
            return false;
        }

        /* 验证成功 */
        UserThreadLocal.set(UserThreadLocal.KEY_USER_ID,String.valueOf(userId));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
