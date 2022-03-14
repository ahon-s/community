package com.paper.admin.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    public Long id;
    public String accountId;
    public String name;
    public String token;
    public Long gmtCreate;
    public Long gmtModified;
    public String avatarUrl;

}
