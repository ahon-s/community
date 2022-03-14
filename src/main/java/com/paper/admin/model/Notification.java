package com.paper.admin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Notification {
    @TableId(type = IdType.AUTO)
    public Long id;
    public Long notifier;
    public Long receiver;
    public Long outerid;
    public Integer type;
    public Long gmtCreate;
    public Integer status;
    public String notifierName;
    public String outerTitle;

}
