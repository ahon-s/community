package com.paper.admin.dto;

import com.paper.admin.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    public Long id;
    public Long parentId;
    public Integer type;
    public Long commentator;
    public Long gmtCreate;
    public Long gmtModified;
    public Long likeCount;
    public String content;
    public User user;
    public Integer commentCount;
}
