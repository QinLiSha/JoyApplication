package com.example.administrator.joyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseAdapter;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.util.MyImageLoader;

/**
 * Created by Administrator on 2016/9/20.
 */
public class PictureAdapter extends MyBaseAdapter<News> {

    public PictureAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_pic_gridview,null);
            vh = new ViewHolder();
            vh.iv_gridview_pic = (ImageView) convertView.findViewById(R.id.iv_gridview_pic);
            convertView.setTag(vh);

        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        new MyImageLoader(context).display(getItem(position).getIcon(),vh.iv_gridview_pic);
        return convertView;
    }

    public class ViewHolder{
       ImageView iv_gridview_pic;
    }
}
