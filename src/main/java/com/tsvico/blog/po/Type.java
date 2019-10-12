package com.tsvico.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 12:35
 * 功能 博客分类
 */
@Entity //和数据库建立对应
@Table(name = "t_type") //更改默认数据库生成的名字
public class Type {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type")  //关系被维护端，一对多,被维护Blog和type之间关系
    private List<Blog> blogs = new ArrayList<>();

    public Type() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
