package com.tsvico.blog.interceptor;

import com.tsvico.blog.util.Constants;
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



    /**
     *  重写请求，对其预处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute(Constants.config.UserSession.getValue()) == null){
            //如果没有登录，那么久重定向到页面
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
