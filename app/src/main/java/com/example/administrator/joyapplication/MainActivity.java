package com.example.administrator.joyapplication;

import android.os.Bundle;

import com.example.administrator.joyapplication.base.MyBaseActivity;

public class MainActivity extends MyBaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showLoadingDialog(MainActivity.this,"数据加载中....",false);
    }
}
