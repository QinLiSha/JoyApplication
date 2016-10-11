package com.example.administrator.joyapplication.model.model.parser;

import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.Comment;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.model.model.NewsType;
import com.example.administrator.joyapplication.model.model.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 * parser:剖析器
 */
public class ParserNews {


    /**
     * 获得新闻类型
     *
     * @param json
     * @return
     */
    public static List<SubType> getNewType(String json) {
        Gson gson = new Gson();
//      这是只显示一个的
        BaseEntry<List<NewsType>> list = gson.fromJson(json, new
                TypeToken<BaseEntry<List<NewsType>>>() {
                }.getType());
        return list.getData().get(0).getSubgrp();
    }

    public static List<NewsType> getNewsType(String json) {
        Gson gson = new Gson();
        BaseEntry<List<NewsType>> entity = gson.fromJson(json, new
                TypeToken<BaseEntry<List<NewsType>>>() {
        }.getType());
        return  entity.getData();
    }

    /**
     * 获得首页新闻列表
     *
     * @param json
     * @return
     */
    public static List<News> getNewList(String json) {
        Gson gson = new Gson();
        BaseEntry<List<News>> listNews = gson.fromJson(json, new TypeToken<BaseEntry<List<News>>>
                () {
        }.getType());
        return listNews.getData();
    }

    /**
     * 获得评论列表
     *
     * @param json
     * @return
     */
    public static List<Comment> getCommentList(String json) {
        Gson gson = new Gson();
        BaseEntry<List<Comment>> commentList = gson.fromJson(json, new
                TypeToken<BaseEntry<List<Comment>>>
                () {
        }.getType());
        return commentList.getData();
    }


    /**
     * 获得跟帖数
     *
     * @param json
     * @return
     */
    public static int getCommentNum(String json) {
        Gson gson = new Gson();
        BaseEntry<Integer> commentNum = gson.fromJson(json, new TypeToken<BaseEntry<Integer>>
                () {
        }.getType());
        return commentNum.getData();
    }

}
