package com.example.administrator.joyapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.adapter.GuidePagerAdapter;
import com.example.administrator.joyapplication.base.MyBaseActivity;


public class GuidePageActivity extends MyBaseActivity {

    private ViewPager viewPager;
    private GuidePagerAdapter adapter;
    private ImageView[] images = new ImageView[4];
    private boolean isFromClassName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断从哪个页面进入的当前页面
        Intent in = getIntent();
        String className = in.getStringExtra("ClassName");
        if (className != null && className.equals("SettingActivity")) {// 证明是settingActivity进入的
            isFromClassName = true;
        }

        SharedPreferences sp = getSharedPreferences("lead_config",
                Context.MODE_PRIVATE);

        boolean isFirst = sp.getBoolean("isFirst", true);// 取出ShareP里面存储的数据

        if (!isFirst) {
            Intent intent = new Intent(GuidePageActivity.this, LogoActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_guide_page);
            initUI();
            init();
            initData();
        }


    }

    /**
     * 记录是否是第一次登陆页面
     */
    public void saveSharedPreference() {
        // SharedPreferences:它可以存储一种简单的数据,SqlLite数据库的存储
        SharedPreferences sp = getSharedPreferences("lead_config",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();// edit方法返回一个可以操作的数据
        editor.putBoolean("isFirst", false);
        editor.commit();// 最后要将保存的数据进行一个提交
    }

    /**
     * 初始化用户界面
     */
    private void initUI() {
        images[0] = (ImageView) findViewById(R.id.iv_smallicon1);
        images[1] = (ImageView) findViewById(R.id.iv_smallicon2);
        images[2] = (ImageView) findViewById(R.id.iv_smallicon3);
        images[3] = (ImageView) findViewById(R.id.iv_smallicon4);

        viewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        images[0].setAlpha(1f);
        images[1].setAlpha(0.2f);
        images[2].setAlpha(0.2f);
        images[3].setAlpha(0.2f);
    }

    private void init() {
        adapter = new GuidePagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(listener);// 设置ViewPager的监听器

    }

    /**
     * 初始化数据
     */
    private void initData() {
        ImageView imageView = null;

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpager_item, null);
        imageView.setBackgroundResource(R.drawable.small);
        adapter.addToAdapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpager_item, null);
        imageView.setBackgroundResource(R.drawable.bd);
        adapter.addToAdapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpager_item, null);
        imageView.setBackgroundResource(R.drawable.wy);
        adapter.addToAdapterView(imageView);

        imageView = (ImageView) getLayoutInflater().inflate(R.layout.viewpager_item, null);
        imageView.setBackgroundResource(R.drawable.welcome);
        adapter.addToAdapterView(imageView);

        adapter.notifyDataSetChanged();
        //将Adapter进行刷新
    }

    private void setPoint(int index) {
        for (int i = 0; i < images.length; i++) {
            if (i == index) {
                images[i].setAlpha(1f);
            } else {
                images[i].setAlpha(0.2f);
            }
        }
    }

    /**
     * ViewPager的监听器
     */
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int arg0) { // 当页面滑动完成后，调用当前的方法,
            // arg0代表滑动完成后的页面的索引值
            setPoint(arg0);
            if (arg0 >= 3) {
                openActivity(LogoActivity.class);
                saveSharedPreference();
                finish();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };


}
