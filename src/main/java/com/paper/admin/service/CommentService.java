package com.paper.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paper.admin.dto.CommentDTO;
import com.paper.admin.enums.CommentTypeEnum;
import com.paper.admin.model.Comment;
import com.paper.admin.model.User;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CommentService extends IService<Comment> {
    void insert(Comment comment, User commentator);
    List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type);
    Integer upCommentCount(Long id);
}
