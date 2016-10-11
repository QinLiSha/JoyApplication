package com.example.administrator.joyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseAdapter;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.util.MyImageLoader;

/**
 * Created by Administrator on 2016/9/9.
 */
public class HomeListViewAdapter extends MyBaseAdapter<News> {

    public HomeListViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_home_lvitem,null);
            vh = new ViewHolder();
            vh.iv_icon_item = (ImageView) convertView.findViewById(R.id.iv_icon_item);
            vh.tv_title_item = (TextView) convertView.findViewById(R.id.tv_title_item);
            vh.tv_summary_item = (TextView) convertView.findViewById(R.id.tv_summary_item);
            vh.tv_time_item = (TextView) convertView.findViewById(R.id.tv_time_item);
            convertView.setTag(vh);

        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_summary_item.setText(getItem(position).getSummary());
        vh.tv_title_item.setText(getItem(position).getTitle());
        vh.tv_time_item.setText(getItem(position).getStamp());
        new MyImageLoader(context).display(getItem(position).getIcon(),vh.iv_icon_item);
        return convertView;
    }

    public class ViewHolder{
        TextView tv_title_item,tv_summary_item,tv_time_item;
        ImageView iv_icon_item;
    }
}
