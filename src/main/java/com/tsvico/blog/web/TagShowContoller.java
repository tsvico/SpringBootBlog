package com.tsvico.blog.web;

import com.tsvico.blog.Controller404;
import com.tsvico.blog.po.Tag;
import com.tsvico.blog.service.BlogService;
import com.tsvico.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/23 20:00
 * 功能
 */
@Controller
public class TagShowContoller {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String tag(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                      @PathVariable("id") String id, Model model){
        List<Tag> tags = tagService.listTop(300);
        Long newId;
        try {
            newId = Long.parseLong(id);
        }catch (Exception e){
            throw new Controller404();
        }
        if (newId==-1){
            newId = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(newId,pageable));
        model.addAttribute("activeTagid", newId);
        return "tags";
    }
}
