package com.tsvico.blog.web;

import com.tsvico.blog.Controller404;
import com.tsvico.blog.po.Comment;
import com.tsvico.blog.po.JsonDate;
import com.tsvico.blog.po.User;
import com.tsvico.blog.service.BlogService;
import com.tsvico.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/21 20:18
 * 功能
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;
    private String avatar = "https://api.adorable.io/avatars/100/";

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable String blogId, Model model,
                           HttpSession session){
        if (session.getAttribute("user")!=null){
            model.addAttribute("login",true); //把登录状态返回前端
        }else {
            model.addAttribute("login",false);
        }
        Long new_id;
        try {
            new_id = Long.parseLong(blogId);
        }catch (Exception e){
            throw new Controller404();
        }
        model.addAttribute("comments",commentService.listCommentByBlogId(new_id));
        return "blog :: commentList";
    }

    @ResponseBody  //在 Controller 类上面用 @RestController 定义或者在方法上面用 @ResponseBody 定义，表明是在 Body 区域输出数据 表明是返回JSON
    @RequestMapping("/comments/{commentId}/delete")
    public JsonDate delete(@PathVariable Long commentId, HttpSession session){
        JsonDate json = new JsonDate();
        if (session.getAttribute("user")==null){
            json.setCode(0);
            json.setMessage("权限异常");
            //return json;
            throw new Controller404();
        }
        try {
            commentService.deleteComment(commentId);
            json.setCode(200);
            json.setMessage("删除成功");
        }catch (Exception e){
            json.setCode(0);
            json.setMessage("error:"+e.getMessage());
        }
        return json;
    }
    @PostMapping("/comments")
    public String post(Comment comment,HttpSession session){
        Long blogId = comment.getBlog().getId();
        String email = comment.getEmail();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar+email);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
