package com.tsvico.blog.web;

import com.tsvico.blog.Controller404;
import com.tsvico.blog.po.Blog;
import com.tsvico.blog.service.BlogService;
import com.tsvico.blog.service.TagService;
import com.tsvico.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author GWJ
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(5));
        return "index";
    }

    @GetMapping("/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        if (query==null) {
            throw new Controller404();
        }
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable String id, Model model
            , HttpSession session, HttpServletResponse response){
        if (session.getAttribute("user")!=null){
            //把登录状态返回前端
            model.addAttribute("login",true);
        }else {
            model.addAttribute("login",false);
        }
        try{
            Long l = Long.parseLong(id);
            Blog blog = blogService.getBlog(l);
            int view = blogService.Views(l);
            if (blog!=null){
                model.addAttribute("blog",blog);
            }
            else{
                throw new Controller404();
            }
        }catch (Exception e){
            throw new Controller404();
        }
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        List<Blog> b = blogService.listRecommendBlogTop(3);
        if (b.size()>0){
            model.addAttribute("newblogs",b);
        }else{
            Blog e = new Blog();
            e.setId((long) 0);
            e.setTitle("暂无内容");
            b.add(e);
        }
        return "_fragments :: newblogList";
    }
}
