package com.paper.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paper.admin.bean.User;

public interface UserService extends IService<User>  {

    public User GetUserByName(String name);

}
