package com.paper.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paper.admin.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper extends BaseMapper<Question> {
//    @Select("select * from question")
//    public List<Question> getQuestions();
    @Select("select * from question order by gmt_create desc limit #{offset},#{size}")
    List<Question> PageList(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} order by gmt_create desc limit #{offset},#{size}")
    List<Question> ListByUserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer countByUserId(Long userId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Long Id);

    @Update("update question set view_count = view_count + 1 where id = #{id}")
    Integer upViewCount(@Param("id")Long id);
    @Update("update question set comment_count = comment_count + 1 where id = #{id}")
    Integer upCommentCount(@Param("id")Long id);

    @Select("select * from question where id != #{id} and tag regexp #{tag}")
    List<Question> selectRelated(Question question);
}
