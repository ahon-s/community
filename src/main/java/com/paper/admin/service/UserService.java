package com.paper.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paper.admin.model.User;

public interface UserService extends IService<User>  {
    User findByToken(String token);

//    public User GetUserByName(String name);

}
