package com.example.administrator.joyapplication.util.dbutil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.model.model.SubType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class DBTools {
    private Context context;
    private DBManager dbManager;
    private SQLiteDatabase sb;

    public DBTools(Context context) {
        this.context = context;
        dbManager = new DBManager(context);
    }

    /************************
     * 收藏
     ********************/
    public boolean saveLocalFavorite(News news) {
        sb = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWSFAVORITE_NAME + " where nid = ?";
        Cursor c = sb.rawQuery(sql, new String[]{news.getNid() + ""});
        if (c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", news.getType());
        contentValues.put("nid", news.getNid());
        contentValues.put("summary", news.getSummary());
        contentValues.put("icon", news.getIcon());
        contentValues.put("stamp", news.getStamp());
        contentValues.put("title", news.getTitle());
        contentValues.put("link", news.getLink());
        sb.insert(DBManager.NEWSFAVORITE_NAME, null, contentValues);


        return true;
    }

    public List<News> getLocalFavorite() {
        List<News> listNews = new ArrayList<News>();
        sb = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWSFAVORITE_NAME;
        Cursor c = sb.rawQuery(sql, null);
//        Cursor c = sb.query(DBManager.NEWSFAVORITE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int nid = c.getInt(c.getColumnIndex("nid"));
                int type = c.getInt(c.getColumnIndex("type"));
                String summary = c.getString(c.getColumnIndex("summary"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String title = c.getString(c.getColumnIndex("title"));
                String link = c.getString(c.getColumnIndex("link"));
                News news = new News(summary, icon, stamp, title, nid, type, link);
                listNews.add(news);
            } while (c.moveToNext());
        }
        c.close();
        sb.close();
        return listNews;
    }

    /********************
     * 新闻首页
     *********************************/
    public boolean saveLocalNews(News news) {
        sb = dbManager.getReadableDatabase();
        String sql = "select nid from " + DBManager.NEWS_NAME + " where nid = ?";
        Cursor c = sb.rawQuery(sql, new String[]{news.getNid() + ""});
        if (c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", news.getType());
        contentValues.put("nid", news.getNid());
        contentValues.put("summary", news.getSummary());
        contentValues.put("icon", news.getIcon());
        contentValues.put("stamp", news.getStamp());
        contentValues.put("title", news.getTitle());
        contentValues.put("link", news.getLink());
        sb.insert(DBManager.NEWS_NAME, null, contentValues);


        return true;
    }

    public List<News> getLocalNews() {
        List<News> listNews = new ArrayList<News>();
        sb = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWS_NAME;
        Cursor c = sb.rawQuery(sql, null);
//        Cursor c = sb.query(DBManager.NEWSFAVORITE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int nid = c.getInt(c.getColumnIndex("nid"));
                int type = c.getInt(c.getColumnIndex("type"));
                String summary = c.getString(c.getColumnIndex("summary"));
                String icon = c.getString(c.getColumnIndex("icon"));
                String stamp = c.getString(c.getColumnIndex("stamp"));
                String title = c.getString(c.getColumnIndex("title"));
                String link = c.getString(c.getColumnIndex("link"));
                News news = new News(summary, icon, stamp, title, nid, type, link);
                listNews.add(news);
            } while (c.moveToNext());
        }
        c.close();
        sb.close();
        return listNews;
    }



    /********************
     * 新闻标题
     *********************************/
    public boolean saveLocalSubType(SubType subType) {
        sb = dbManager.getReadableDatabase();
        String sql = "select subid from " + DBManager.NEWSTYPE_NAME + " where subid = ?";
        Cursor c = sb.rawQuery(sql, new String[]{subType.getSubid() + ""});
        if (c.moveToNext()) {
            return false;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("subid", subType.getSubid());
        contentValues.put("subgroup", subType.getSubgroup());
        sb.insert(DBManager.NEWSTYPE_NAME, null, contentValues);


        return true;
    }

    public List<SubType> getLocalType() {
        List<SubType> listNews = new ArrayList<SubType>();
        sb = dbManager.getReadableDatabase();
        String sql = "select * from " + DBManager.NEWSTYPE_NAME;
        Cursor c = sb.rawQuery(sql, null);
//        Cursor c = sb.query(DBManager.NEWSFAVORITE_NAME, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int subid = c.getInt(c.getColumnIndex("subid"));
                String subgroup = c.getString(c.getColumnIndex("subgroup"));
                SubType subType =new SubType(subgroup,subid);
                listNews.add(subType);
            } while (c.moveToNext());
        }
        c.close();
        sb.close();
        return listNews;
    }
}
