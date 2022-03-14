package com.paper.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @TableId(type = IdType.AUTO)
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
}
