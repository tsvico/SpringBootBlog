package com.tsvico.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/12 16:16
 * 添加 切面，拦截访问外部类 拦截范围Pointcut定义的集合
 */
@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.tsvico.blog.web.*.*(..))")
    public void log(){
    }
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0){
            ip = request.getRemoteAddr();
        }
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("结果 ： {}",requestLog);
    }

    @After("log()")
    public void doAfter(){
        //logger.info("-----------------doAfter-----------");
    }

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterRuturn(Object result){
        //拦截返回结果，并打印日志
        logger.info("方法返回结果 ： {}",result);
    }
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod; //请求对象
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }
        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
