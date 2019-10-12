package com.tsvico.blog.web.admin;

import com.tsvico.blog.Controller404;
import com.tsvico.blog.po.JsonDate;
import com.tsvico.blog.po.User;
import com.tsvico.blog.service.UserService;
import com.tsvico.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/26 21:35
 * 功能
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    //private UserService userService;
    @Autowired
    private UserService userService;

    @Value("${config.salt}")
    private String salt;

    @GetMapping("user")
    public String getAdmin(Model model,HttpSession session){
        //model.addAttribute("user",userService.getUser());
        model.addAttribute("user",(User) session.getAttribute("user"));
        return "admin/user";
    }


    @ResponseBody  //在 Controller 类上面用 @RestController 定义或者在方法上面用 @ResponseBody 定义，表明是在 Body 区域输出数据 表明是返回JSON
    @RequestMapping("userUpdate")
    public JsonDate UserUpdate(@RequestParam Map<String,String> person) {
        if (person.size()==0){
            throw new Controller404();
        }

        String uuname = person.get("uuname"); //原来的用户名
        String username = person.get("username");
        String password = person.get("password");
        String password2 = person.get("password2");
        JsonDate jsonDate = new JsonDate();
        User user = userService.findUser(uuname);
        user.setUsername(username);
        if (password!=null&&password.length()>0){
            if (MD5Utils.code(salt+password+salt).equals(user.getPassword())){
                password2 = MD5Utils.code(salt+password2+salt);
                user.setPassword(password2);
            }else {
                jsonDate.setCode(0);
                jsonDate.setMessage("更新失败：原密码错误");
                return jsonDate;
            }
        }
        user.setUsername(username);
        user.setNickname(person.get("nickname"));
        user.setAvatar(person.get("avatar"));
        user.setEmail(person.get("email"));
        user.setUpdateTime(new Date());
        try{
            User n = userService.updateUser(uuname,user);
            if (n!=null){
                jsonDate.setCode(200);
                jsonDate.setMessage("更新成功");
            }
        }catch (Exception e){
            jsonDate.setCode(0);
            jsonDate.setMessage(""+e.getMessage());
        }

        return jsonDate;
    }
}
