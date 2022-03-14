package com.paper.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paper.admin.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CommentMapper  extends BaseMapper<Comment> {
    @Update("update comment set comment_count = comment_count + 1 where id = #{id}")
    Integer upCommentCount(Long id);

}
