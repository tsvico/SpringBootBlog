package com.tsvico.blog.web.admin;

import com.tsvico.blog.Controller404;
import com.tsvico.blog.po.JsonDate;
import com.tsvico.blog.po.User;
import com.tsvico.blog.service.BlogService;
import com.tsvico.blog.service.UserService;
import com.tsvico.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/18 10:06
 * 功能
 */
@RestController
public class AlltoJson {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/admin/blogs/{id}/delete")
    public JsonDate delete(@PathVariable Long id){
        if (id==null){
            throw new Controller404();
        }
        JsonDate jsonDate = new JsonDate();
        try {
            blogService.deleteBlog(id);
            jsonDate.setCode(200);
            jsonDate.setMessage("删除成功");
        }catch (Exception e){
            jsonDate.setCode(0);
            jsonDate.setMessage("删除失败"+e.getMessage());
        }
        return jsonDate;
    }

}
