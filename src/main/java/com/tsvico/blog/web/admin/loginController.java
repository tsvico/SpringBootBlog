package com.tsvico.blog.web.admin;

import com.tsvico.blog.po.JsonDate;
import com.tsvico.blog.po.User;
import com.tsvico.blog.service.UserService;
import com.tsvico.blog.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 18:59
 * 功能 控制器
 */

@Controller
@RequestMapping("/admin")
public class loginController {
    @Autowired
    private UserService userService;

    @Value("${config.admin}")
    private String admin;
    @Value("${config.defaultUser.username}")
    private String defaultUser_usernmae;
    @Value("${config.defaultUser.password}")
    private String defaultUser_password;
    @Value("${config.defaultUser.nickname}")
    private String defaultUser_nickname;

    @GetMapping
    public String loginPage(HttpSession session){
        if (session.getAttribute("user")!=null){
            System.out.println(session.getAttribute("user"));
            return "admin/index";
        }
        User user = userService.findUser("admin");
        if (user==null){
            user = new User();
            user.setUsername(defaultUser_usernmae);
            user.setPassword(defaultUser_password);
            user.setNickname(defaultUser_nickname);
            user.setAvatar("https://api.adorable.io/avatars/100/admin");
            userService.savaUser(user);
            System.out.println(defaultUser_password);
        }
        return admin+"/login";
    }

    @PostMapping("/login")
    @ResponseBody  //在 Controller 类上面用 @RestController 定义或者在方法上面用 @ResponseBody 定义，表明是在 Body 区域输出数据 表明是返回JSON
    public JsonDate login(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          HttpServletRequest request,
                          HttpServletResponse response){
        JsonDate jsonDate = new JsonDate();
        if (username==null||password==null){
            //return "redirect:/login";
            jsonDate.setCode(1);
            jsonDate.setMessage("密码为空");
            return jsonDate;
        }
        if (!CodeUtil.checkVerifyCode(request)) {
            jsonDate.setCode(0);
            jsonDate.setMessage("验证码有误！");
            return jsonDate;
        }
        User user = userService.checkUser(username,password);
        if (user!=null){
            user.setPassword(null); //把密码设置空，账户存入session，表示登陆成功
            session.setAttribute("user",user);
            Cookie cookie = new Cookie("isLogin","1");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            jsonDate.setCode(2);
            String url = admin;
            HashMap<String, String> data = new HashMap<>();
            data.put("url",url);
            jsonDate.setData(data);
            jsonDate.setMessage("登陆成功");
            //return admin+"/index";
        }else {
            //attributes.addFlashAttribute("message","用户名或密码不正确");
            //return "redirect:/"+admin; //重定向
            jsonDate.setCode(0);
            String url = admin+"/index";
            HashMap<String, String> data = new HashMap<>();
            data.put("url",url);
            jsonDate.setData(data);
            jsonDate.setMessage("用户名或密码不正确");
        }
        return jsonDate;
    }

    @GetMapping("/login")
    public String login(){
        return "redirect:/"+admin;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,HttpServletResponse response){
        session.removeAttribute("user");
        Cookie cookie = new Cookie("isLogin","0");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/"+admin;
    }

}
