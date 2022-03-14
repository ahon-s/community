package com.paper.admin.dto;

import com.paper.admin.model.User;
import lombok.Data;

@Data
public class QuestionDTO {

    public Long id;
    public String title;
    public String description;
    public String tag;
    public Long creator;
    public Long gmtCreate;
    public Long gmtModified;
    public Integer viewCount;
    public Integer commentCount;
    public Integer likeCount;
    public User user;
}
