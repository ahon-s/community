package com.paper.admin.dto;

import com.paper.admin.model.User;
import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}