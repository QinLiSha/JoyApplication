package com.example.administrator.joyapplication.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.administrator.joyapplication.MainActivity;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseActivity;

public class LogoActivity extends MyBaseActivity {

    private ImageView iv_login_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        iv_login_logo = (ImageView) findViewById(R.id.iv_login_logo);
        AlphaAnimation alp = new AlphaAnimation(0, 1);
        alp.setDuration(1500);
        iv_login_logo.setAnimation(alp);
        alp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {  }
            @Override
            public void onAnimationRepeat(Animation animation) {   }
            @Override
            public void onAnimationEnd(Animation animation) {
                openActivity(HomeActivity.class);
                finish();
            }
        });
    }
}