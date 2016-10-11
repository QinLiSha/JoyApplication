package com.example.administrator.joyapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseAdapter;
import com.example.administrator.joyapplication.model.model.Loginlog;

/**
 * Created by Administrator on 2016/9/9.
 */
public class LoginAdapter extends MyBaseAdapter<Loginlog> {
    public LoginAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHold vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_login_item, null);

            vh = new ViewHold();
            vh.tv_loginTime = (TextView) convertView.findViewById(R.id.tv_loginTime);
            vh.tv_loginAddress = (TextView) convertView.findViewById(R.id.tv_loginAddress);
            vh.tv_loginDevice = (TextView) convertView.findViewById(R.id.tv_loginDevice);
            convertView.setTag(vh);
        } else {
            vh = (ViewHold) convertView.getTag();
        }
        vh.tv_loginTime.setText(getItem(position).getTime());
        vh.tv_loginAddress.setText(getItem(position).getAddress());
        vh.tv_loginDevice.setText(getItem(position).getDevice() + "");
        return convertView;
    }

    class ViewHold {
        TextView tv_loginTime, tv_loginAddress, tv_loginDevice;
    }

}
