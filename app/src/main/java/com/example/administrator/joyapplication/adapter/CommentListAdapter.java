package com.example.administrator.joyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseAdapter;
import com.example.administrator.joyapplication.model.model.Comment;
import com.example.administrator.joyapplication.util.MyImageLoader;

/**
 * Created by Administrator on 2016/9/12.
 */
public class CommentListAdapter extends MyBaseAdapter<Comment> {


    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.layout_comment_item,null);
            vh = new ViewHolder();
            vh.iv_comment_userImg = (ImageView) convertView.findViewById(R.id.iv_comment_userImg);
            vh.tv_comment_userName = (TextView) convertView.findViewById(R.id.tv_comment_userName);
            vh.tv_comment_time = (TextView) convertView.findViewById(R.id.tv_comment_time);
            vh.tv_comment_comment = (TextView) convertView.findViewById(R.id.tv_comment_comment);
            convertView.setTag(vh);

        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_comment_userName.setText(getItem(position).getUid());
        vh.tv_comment_time.setText(getItem(position).getStamp());
        vh.tv_comment_comment.setText(getItem(position).getContent());
        new MyImageLoader(context).display(getItem(position).getPortrait(),vh.iv_comment_userImg);
        return convertView;
    }

    public class ViewHolder{
        TextView tv_comment_userName,tv_comment_time,tv_comment_comment;
        ImageView iv_comment_userImg;
    }

}
