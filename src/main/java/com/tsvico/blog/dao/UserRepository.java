package com.tsvico.blog.dao;

import com.tsvico.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 18:55
 * 功能
 */
public interface UserRepository extends JpaRepository<User,Long> {
    //这样就就可以直接操作数据库查询
    User findByUsernameAndPassword(String username,String password);
    //查用户信息
    User findByUsername(String username);

}
