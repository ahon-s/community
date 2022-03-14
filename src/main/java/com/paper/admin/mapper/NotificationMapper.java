package com.paper.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paper.admin.model.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NotificationMapper extends BaseMapper<Notification> {

    @Select("select * from notification order by gmt_create desc limit #{offset},#{size}")
    List<Notification> PageList(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification")
    Integer count();

    @Select("select * from notification where receiver = #{userId} order by gmt_create desc limit #{offset},#{size}")
    List<Notification> ListByUserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from notification where receiver = #{userId}")
    Integer countByUserId(Long userId);

    @Select("select * from notification where id = #{id}")
    Notification getById(@Param("id") Long Id);

}
