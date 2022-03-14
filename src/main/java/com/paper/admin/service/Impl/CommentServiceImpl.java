package com.paper.admin.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paper.admin.dto.CommentDTO;
import com.paper.admin.enums.CommentTypeEnum;
import com.paper.admin.enums.NotificationStatusEnum;
import com.paper.admin.enums.NotificationTypeEnum;
import com.paper.admin.exception.CustomizeErrorCode;
import com.paper.admin.exception.CustomizeException;
import com.paper.admin.mapper.CommentMapper;
import com.paper.admin.mapper.NotificationMapper;
import com.paper.admin.mapper.QuestionMapper;
import com.paper.admin.mapper.UserMapper;
import com.paper.admin.model.Comment;
import com.paper.admin.model.Notification;
import com.paper.admin.model.Question;
import com.paper.admin.model.User;
import com.paper.admin.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper,Comment> implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;


    @Override
    @Transactional
    public void  insert(Comment comment,User commentator){
        //判断评论的父级是否存在
        if (null == comment.getParentId() || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        //判断评论类型是否存在
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException((CustomizeErrorCode.TYPE_PARAM_WRONG));
        }

        //子回复
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()){

            //通过parentId查询父评论是否存在
            Comment dbComment = commentMapper.selectById(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException((CustomizeErrorCode.COMMENT_NOT_FOUND));
            }
            //获取父评论的问题
            Question question = questionMapper.selectById(dbComment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            //插入评论
            commentMapper.insert(comment);
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
            commentMapper.upCommentCount(comment.getParentId());
        }else {//问题回复
            Question question = questionMapper.selectById(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
            //评论数量 1
            questionMapper.upCommentCount(comment.getParentId());
        }
    }



    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
        if (receiver == comment.getCommentator()) {
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }


    @Override
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
        commentWrapper
                .eq("parent_id",id)
                .eq("type", type.getType());
        commentWrapper.orderByDesc("gmt_create");
        List<Comment> comments = commentMapper.selectList(commentWrapper);
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //通过set去重评论人
        Set<Long> commentators = new HashSet<>();
        for (Comment comment : comments) {
            Long commentator = comment.getCommentator();
            commentators.add(commentator);
        }

        // 获取评论人并转换为 Map
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper
                .in("id",commentators);
        List<User> users = userMapper.selectList(userWrapper);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }

    @Override
    public Integer upCommentCount(Long id) {
        return commentMapper.upCommentCount(id);
    }
}
