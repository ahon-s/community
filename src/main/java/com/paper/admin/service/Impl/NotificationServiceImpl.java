package com.paper.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paper.admin.dto.NotificationDTO;
import com.paper.admin.dto.PaginationDTO;
import com.paper.admin.enums.NotificationStatusEnum;
import com.paper.admin.enums.NotificationTypeEnum;
import com.paper.admin.exception.CustomizeErrorCode;
import com.paper.admin.exception.CustomizeException;
import com.paper.admin.mapper.NotificationMapper;
import com.paper.admin.mapper.QuestionMapper;
import com.paper.admin.model.Notification;
import com.paper.admin.model.Question;
import com.paper.admin.model.User;
import com.paper.admin.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {


    @Autowired
    NotificationMapper notificationMapper;

    //获得回复列表
    @Override
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        Integer offset = size*(page-1);
//        List<Question> questions = this.list();

        //数据库逻辑判断 防止越界
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        Integer totalCount = notificationMapper.countByUserId(userId);
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) page = 1;
        paginationDTO.setPagination(totalPage,page);
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();

        List<Notification>  notifications = notificationMapper.ListByUserId(userId,offset,size);
        if (notifications.size() == 0){
            return paginationDTO;
        }
        List<NotificationDTO> notificationDTOS= new ArrayList<>();
        for (Notification notification : notifications){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }
    //获得未读回复数
    @Override
    public Long unreadCount(Long userId) {

        QueryWrapper<Notification> notificationWrapper = new QueryWrapper<>();
        notificationWrapper
                .eq("receiver",userId)
                .eq("status",0);
        return notificationMapper.selectCount(notificationWrapper);
    }
    //将回复status设置未已读
    @Override
    public NotificationDTO read(Long id, User user) {

        Notification notification = notificationMapper.selectById(id);
        if (notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(),user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return  notificationDTO;
    }
}
