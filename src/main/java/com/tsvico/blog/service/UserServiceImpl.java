package com.tsvico.blog.service;

import com.tsvico.blog.dao.UserRepository;
import com.tsvico.blog.po.User;
import com.tsvico.blog.util.MD5Utils;
import com.tsvico.blog.util.MyBeanUtils;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tsvico
 * @email tsxygwj@gmail.com
 * @time 2019/8/13 18:52
 * 功能
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${config.salt}")
    private String salt;

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(salt+password+salt)); //密码加盐
    }

    @Transactional //事务
    @Override
    public User updateUser(String username, User use) throws NotFoundException {
        User user = userRepository.findByUsername(username);
        if (user==null){
            throw new NotFoundException("该用户不存在");
        }
        BeanUtils.copyProperties(use,user,MyBeanUtils.getNullPropertyNames(use));
        return userRepository.save(user);
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public void savaUser(User user) {
        String npass = MD5Utils.code(user.getPassword());
        user.setPassword(MD5Utils.code(salt+npass+salt));
        userRepository.save(user);
    }



}
