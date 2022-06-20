package me.rvj.blog.config;

import me.rvj.blog.handle.IPInterceptor;
import me.rvj.blog.handle.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @program: rv-blog
 * @description:
 * @author: Rv_Jiang
 * @date: 2022/5/21 15:17
 */

@Configuration
public class WebMVCConfig  implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private IPInterceptor ipInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        跨域配置
        registry.addMapping("/**")
//                .allowedOrigins("http:/localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ipInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/articles/update");
//                .excludePathPatterns("/login","/register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:///D:/RvBlogImages/");
    }
}
