package com.example.administrator.joyapplication.gloable;

import com.example.administrator.joyapplication.util.ShareUtil;

import java.util.ServiceConfigurationError;

/**
 * Created by Administrator on 2016/9/5.
 * http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=864394101849794
 *
 * http://118.244.212.82:9092/newsClient/news_sort?ver=1&imei=864394101849794
 */
public class API {
    public static final String ServerIP = "http://118.244.212.82:9092/newsClient/";
    /**
     * 首页列表
     */
    public static final String NEWS_LIST =ServerIP +"news_list?";
    /**
     * 标题列表
     */
    public static final String NEWS_SORT =ServerIP +"news_sort?";
    /**
     * 用户登录的接口
     */
    public static final String USER_LOGIN =ServerIP +"user_login?";
    /**
     * 用户注册的接口
     */
    public static final String USER_REGISTER = ServerIP+"user_register?";

    /**
     * 用户中心数据
     */
    public static final String USER_CENTER_DATA= ServerIP+"user_home?";
    /**
     * 忘记密码接口
     * http://118.244.212.82:9092/newsClient/user_forgetpass?ver=版本号&email=邮箱
     */
    public static final String USER_FORGET_PASS= ServerIP+"user_forgetpass?";
    /**
     * 跟帖数数量   cmt_num?ver=版本号& nid=新闻编号
     */
    public static final String COMMENTS_NUMBER = ServerIP+"cmt_num?";
    /**
     * 跟帖界面接口
     */
    public static final String COMMENTS_DATA= ServerIP+"cmt_list?";
    /**
     * 发表评论接口
     */
    public static final String COMMENTS_CONTENT= ServerIP+"cmt_commit?";




}
