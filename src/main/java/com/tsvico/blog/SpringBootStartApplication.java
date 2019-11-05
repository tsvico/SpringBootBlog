package com.tsvico.blog;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/9/5 19:19
 * 功能  修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(BlogApplication.class);
    }
    */
}
