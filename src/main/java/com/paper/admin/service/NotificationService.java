package com.paper.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.paper.admin.dto.NotificationDTO;
import com.paper.admin.dto.PaginationDTO;
import com.paper.admin.model.Notification;
import com.paper.admin.model.User;
import org.springframework.stereotype.Service;


public interface NotificationService extends IService<Notification> {
    PaginationDTO list(Long id, Integer page, Integer size);
    Long unreadCount(Long id);

    NotificationDTO read(Long id, User user);
}
