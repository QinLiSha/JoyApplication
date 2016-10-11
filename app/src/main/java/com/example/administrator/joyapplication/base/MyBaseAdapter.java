package com.example.administrator.joyapplication.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter基类
 * Created by Administrator on 2016/9/6.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> myList = new ArrayList<T>();

    public MyBaseAdapter(Context context) {
        this.context = context;
//        layoutInflater = (LayoutInflater) context.getSystemService(Context
// .LAYOUT_INFLATER_SERVICE);
        this.layoutInflater = LayoutInflater.from(context);
    }

    /**
     * 获得Adapter的所有数据
     *
     * @return
     */
    public List<T> getMyList() {
        return myList;
    }

    /**
     * 封装一个添加一条数据的方法
     *
     * @param t          添加的那条数据
     * @param isClearOld 是否要清理就得数据
     */
    public void appendDated(T t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            myList.clear();
        myList.add(t);
    }

    /**
     * 封装一个添加多条数据的方法
     *
     * @param t          添加的那多条数据
     * @param isClearOld 是否要清理就得数据
     */
    public void appendDated(List<T> t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            myList.clear();
        myList.addAll(t);
    }

    /**
     * 封装一个往数据顶头添加一条数据的方法
     *
     * @param t
     * @param isClearOld
     */
    public void appendDataTop(T t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            myList.clear();
        myList.add(0, t);
    }

    public void appendDataTop(List<T> t, boolean isClearOld) {
        if (t == null)
            return;
        if (isClearOld)
            myList.clear();
        myList.addAll(0, t);
    }

    public void isClear() {
        myList.clear();
    }

    public void updateAdapter() {
        this.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (myList != null)
            return myList.size();
        return 0;
    }

    @Override
    public T getItem(int position) {
        if (myList == null || myList.size() < 0)
            return null;
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        return getMyView(position, view, parent);
    }

    public abstract View getMyView(int position, View convertView, ViewGroup parent);
}
