package com.example.administrator.joyapplication.model.model;

/**
 * Created by Administrator on 2016/9/13.
 */
public class CommentContent {
    private String token;//用户令牌
    private String comment;//评论内容

    public CommentContent(String token, String comment) {
        this.token = token;
        this.comment = comment;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
