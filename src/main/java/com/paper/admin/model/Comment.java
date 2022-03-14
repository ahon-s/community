package com.paper.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @TableId(type = IdType.AUTO)
    public Long id;
    public Long parentId;
    public Integer type;
    public Long commentator;
    public Long gmtCreate;
    public Long gmtModified;
    public Long likeCount;
    public String content;
    public Integer commentCount;
}
