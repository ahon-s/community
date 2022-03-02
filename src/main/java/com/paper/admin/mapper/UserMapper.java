package com.paper.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paper.admin.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

//@Mapper
//@Component
//public interface UserMapper  {
//
//    @Select("select * from user where username=#{name}")
//    public User GetUserByName(String name);
//}
@Mapper
@Component
public interface UserMapper  extends BaseMapper<User>  {

    @Select("select * from user where username=#{name}")
    public User GetUserByName(String name);
}