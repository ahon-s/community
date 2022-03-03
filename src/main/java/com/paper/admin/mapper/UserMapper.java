package com.paper.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paper.admin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
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

//    @Select("select * from user where username=#{name}")
//    public User GetUserByName(String name);
    @Select("select * from user where token=#{token}")
    public User findByToken(@Param("token") String token);


}