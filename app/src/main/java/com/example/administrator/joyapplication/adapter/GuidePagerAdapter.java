package com.example.administrator.joyapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/2.
 */
public class GuidePagerAdapter extends PagerAdapter{//pager:呼叫机

    private Context context;
    private ArrayList<View> arrayList = new ArrayList<View>();//存储的是一个View,也就是ViewPager每一个页面显示的图片

    public GuidePagerAdapter(Context context ) {
        super();
        this.context = context;
    }

    /**
     * 添加页面
     * @param view
     */
    public void addToAdapterView(View view){
        arrayList.add(view);
    }

    /**
     * 返回ViewPager的页面数量
     * @return
     */
    @Override
    public int getCount() {
        return arrayList.size();
    }

    /**
     * 判断是前后两个页面是否是同一个页面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 如果超出ViewPager缓存页面,将页面销毁
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeViewInLayout(arrayList.get(position));
    }

    /**
     * 将要缓存的View添加到集合中
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(arrayList.get(position));
        return arrayList.get(position);
    }
}
