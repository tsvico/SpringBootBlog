package com.tsvico.blog.service;

import com.tsvico.blog.po.User;
import javassist.NotFoundException;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 18:48
 * 功能 用户登录接口
 */
public interface UserService {
    User checkUser(String username,String password);
    User updateUser(String username, User use) throws NotFoundException;
    User findUser(String username);
    void savaUser(User user);
}
