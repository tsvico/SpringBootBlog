package com.tsvico.blog.service;

import com.tsvico.blog.po.Comment;

import java.util.List;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/21 20:24
 * 功能
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);
    Comment saveComment(Comment comment);
    void deleteComment(Long id);
}
