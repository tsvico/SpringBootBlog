package com.tsvico.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 21:03
 * 功能
 */
@Configuration
public class webConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**") //过滤路径
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/login"); //排除路径
    }
}
