package com.tsvico.blog.web.admin;

import com.tsvico.blog.po.Type;
import com.tsvico.blog.service.TypeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/14 1:52
 * 功能 路由
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String lists(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    @GetMapping("types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result,
                       RedirectAttributes attributes){
        Type type1 = typeService.getTypeByname(type.getName());
        if (type1!=null){
            // 通过BindingResult 验证表单 spring专用
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()){
            //如果有错误   比如提交为空
            return "admin/types-input";
        }
        Type t= typeService.saveType(type);
        if (t == null){
            //没成功
            attributes.addFlashAttribute("message","新增失败");
        }else {
            //成果
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result,
                       @PathVariable Long id,RedirectAttributes attributes){
        Type type1 = typeService.getTypeByname(type.getName());
        if (type1!=null){
            // 通过BindingResult 验证表单 spring专用
            result.rejectValue("name","nameError","不能重复添加");
        }
        if (result.hasErrors()){
            //如果有错误   比如提交为空
            return "admin/types-input";
        }
        Type t= null;
        try {
            t = typeService.updateType(id,type);
            if (t == null){
                //没成功
                attributes.addFlashAttribute("message","更新失败");
            }else {
                //成果
                attributes.addFlashAttribute("message","更新成功");
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/types";
    }


    @GetMapping("types/{id}/delete")
    public String delect(@PathVariable Long id,
                         RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
