package com.tsvico.blog.service;

import com.tsvico.blog.po.Blog;
import com.tsvico.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/16 20:02
 * 功能
 */
public interface BlogService {
    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(String query,Pageable pageable);
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size); //推荐

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
    int Views(Long id);
    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog) throws NotFoundException;

    void deleteBlog(Long id);
}
