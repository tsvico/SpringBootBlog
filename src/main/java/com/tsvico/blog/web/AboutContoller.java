package com.tsvico.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/28 16:20
 * 功能
 */
@Controller
public class AboutContoller {

    @GetMapping("about")
    public String about(){
        return "about";
    }
}
