package com.paper.admin.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paper.admin.model.User;
import com.paper.admin.mapper.UserMapper;
import com.paper.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }
//    @Autowired
//    UserMapper userMapper;
//
//    public User GetUserByName(String name){
//        return userMapper.GetUserByName(name);
//    }

}
