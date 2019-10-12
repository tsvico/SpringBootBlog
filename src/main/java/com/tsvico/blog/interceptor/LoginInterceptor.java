package com.tsvico.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 20:57
 * 功能 拦截所有请求admin
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    //重写请求，对其预处理
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null){
            response.sendRedirect("/admin/login"); //如果没有登录，那么久重定向到页面
            return false;
        }
        return true;
    }
}
