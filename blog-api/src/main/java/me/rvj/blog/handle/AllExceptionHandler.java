package me.rvj.blog.handle;

import me.rvj.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: rv-blog
 * @description: 统一异常处理
 * @author: Rv_Jiang
 * @date: 2022/5/26 9:15
 */

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(500,"服务器繁忙");
    }
}
