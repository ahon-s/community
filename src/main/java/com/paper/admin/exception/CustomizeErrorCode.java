package com.paper.admin.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND( 2001,"你找到问题不在了,请换一个试试..."),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登录不能评论，请先登录"),
    SYS_ERROR(2004,"服务器冒烟了，稍后再试？"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在了，要不要换个试试？"),
    COMMENT_IS_EMPTY(2007,"输入内容不能为空，重新尝试？"),
    READ_NOTIFICATION_FAIL(2008, "这是别人的信息？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？");

    public String message;
    public Integer code;
    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
