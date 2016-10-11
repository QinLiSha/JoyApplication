package com.example.administrator.joyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseAdapter;
import com.example.administrator.joyapplication.model.model.SubType;

/**
 * Created by Administrator on 2016/9/6.
 */
public class NewTypeAdapter extends MyBaseAdapter<SubType> {

    public NewTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHold vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_newtype_item, null);

            vh = new ViewHold();
            vh.tv_newstype_item = (TextView) convertView.findViewById(R.id.tv_newstype_item);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }
        String subGroup = myList.get(position).getSubgroup();
        vh.tv_newstype_item.setText(subGroup);
        return convertView;
    }

    class ViewHold {
        TextView tv_newstype_item;
    }
}
