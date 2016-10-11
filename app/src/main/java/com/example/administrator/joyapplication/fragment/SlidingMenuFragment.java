package com.example.administrator.joyapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.activity.HomeActivity;
import com.example.administrator.joyapplication.activity.LocationActivity;

public class SlidingMenuFragment extends Fragment implements View.OnClickListener {
    private RelativeLayout rl_news, rl_favorite, rl_comment, rl_local, rl_image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_menu, container, false);
        rl_news = (RelativeLayout) view.findViewById(R.id.rl_news);
        rl_favorite = (RelativeLayout) view.findViewById(R.id.rl_favorites);
        rl_comment = (RelativeLayout) view.findViewById(R.id.rl_comment);
        rl_local = (RelativeLayout) view.findViewById(R.id.rl_local);
        rl_image = (RelativeLayout) view.findViewById(R.id.rl_image);
        rl_news.setOnClickListener(this);
        rl_favorite.setOnClickListener(this);
        rl_comment.setOnClickListener(this);
        rl_local.setOnClickListener(this);
        rl_image.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_news:
                ((HomeActivity) getActivity()).showHomeFragment();
                changeColor();
                rl_news.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.rl_favorites:
                ((HomeActivity) getActivity()).showFavoritesFragment();
                changeColor();
                rl_favorite.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.rl_comment:
                changeColor();
                rl_comment.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.rl_local:
                changeColor();
//                Handler handler = new Handler();
                ((HomeActivity) getActivity()).openActivity(LocationActivity.class);
                rl_local.setBackgroundColor(0xc3f3fd);
                break;
            case R.id.rl_image:
                ((HomeActivity) getActivity()).showPictureFragment();
                changeColor();
                rl_image.setBackgroundColor(0xc3f3fd);
                break;
        }
    }

    public void changeColor() {
        rl_news.setBackgroundColor(0x33c85555);
        rl_favorite.setBackgroundColor(0x33c85555);
        rl_comment.setBackgroundColor(0x33c85555);
        rl_local.setBackgroundColor(0x33c85555);
        rl_image.setBackgroundColor(0x33c85555);
    }
}
