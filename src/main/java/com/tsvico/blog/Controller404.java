package com.tsvico.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/27 10:54
 * 功能
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class Controller404 extends RuntimeException {
    //给前端返回404页面

    public Controller404() {
    }

    public Controller404(String message) {
        super(message);
    }

    public Controller404(String message, Throwable cause) {
        super(message, cause);
    }
}
