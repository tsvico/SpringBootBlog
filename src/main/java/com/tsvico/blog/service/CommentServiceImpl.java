package com.tsvico.blog.service;

import com.tsvico.blog.dao.CommentRepository;
import com.tsvico.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/21 20:27
 * 功能
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(id, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) { //有修改后的值
            comment.setParentComment(commentRepository.findById(parentCommentId).get());
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    /**
     * 循环每个顶点的评论结点（只把顶级结点拿出来）
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * root 根节点，blog不为空的对象集合
     *
     * @param comments
     */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                //循环迭代，找出子代，存放在tempReplys中
                recursive(reply);
            }
            //修改顶级结点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            //tempReplys.clear();
            tempReplys = new ArrayList<>(); //子集合
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * 递归迭代，剥洋葱，找出子代
     *
     * @param comment 被迭代对象
     */
    private void recursive(Comment comment) {
        tempReplys.add(comment); //顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursive(reply);
                }
            }
        }
    }
}
