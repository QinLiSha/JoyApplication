package com.example.administrator.joyapplication.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseActivity;
import com.example.administrator.joyapplication.fragment.FavoritesFragment;
import com.example.administrator.joyapplication.fragment.Foundfragment;
import com.example.administrator.joyapplication.fragment.HomeFragment;
import com.example.administrator.joyapplication.fragment.LoginFragment;
import com.example.administrator.joyapplication.fragment.PicturesFragment;
import com.example.administrator.joyapplication.fragment.RegisterFragment;
import com.example.administrator.joyapplication.fragment.SlidingMenuFragment;
import com.example.administrator.joyapplication.fragment.SlidingRightFragment;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends MyBaseActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";
    private RequestQueue mReuestQueue;
    private Fragment homeFragment, slidingFragment, slidingRightFragment, loginFragment,
            registerFragment, favoriteFragment,commentFragment,picturesFragment,foundFragment;
    private static SlidingMenu slidingMenu;
    private ImageView iv_home_left, iv_home_right;
    private TextView tv_home_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String imei = CommonUtil.getIMEI(HomeActivity.this);
        iv_home_left = (ImageView) findViewById(R.id.iv_home_left);
        iv_home_right = (ImageView) findViewById(R.id.iv_home_right);
        tv_home_title = (TextView) findViewById(R.id.tv_home_title);
        loadHomeFragment();
        initSlidingMenu();
        iv_home_right.setOnClickListener(this);
        iv_home_left.setOnClickListener(this);

    }

    private void initSlidingMenu() {
        slidingRightFragment = new SlidingRightFragment();
        slidingFragment = new SlidingMenuFragment();
        slidingMenu = new SlidingMenu(this);//初始化SlidingMenu
//        slidingMenu.setMode(SlidingMenu.LEFT);//设置左滑菜单
        //如果只显示左侧菜单就是用LEFT,右侧就RIGHT，左右都支持就LEFT_RIGHT
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);//设置菜单滑动模式，菜单是出现在左侧还是右侧，还是左右两侧都有
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑动的屏幕范围，该设置为全屏区域都可以滑动
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//SlidingMenu划出时主页面显示的剩余宽度
        slidingMenu.setFadeDegree(0.35f);//SlidingMenu滑动时的渐变程度
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
        slidingMenu.setMenu(R.layout.layout_menu);//设置SlidingMenu的布局文件
        slidingMenu.setSecondaryMenu(R.layout.layout_right);

        getSupportFragmentManager().beginTransaction().add(R.id.slidingMenu_contianer,
                slidingFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.layout_right_contianer,
                slidingRightFragment).commit();

    }

    private void loadHomeFragment() {
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contianer, homeFragment).commit();
    }

    /**
     * 显示收藏页面
     */
    public void showFavoritesFragment() {
        setTitle("我的收藏");
        if (favoriteFragment == null) {
            favoriteFragment = new FavoritesFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, favoriteFragment)
                    .commit();

    }
    /**
     * 显示收藏页面
     */
    public void showPictureFragment() {
        setTitle("我的图片");
        if (picturesFragment == null) {
            picturesFragment = new PicturesFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, picturesFragment)
                .commit();

    }
    /**
     * 显示主页的fragment
     */
    public void showHomeFragment() {
        setTitle("资讯");
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, homeFragment)
                .commit();
    }

    /**
     * 用户登录界面
     */
    public void showLoginFragment() {
        setTitle("用户登录");
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, loginFragment)
                    .commit();
    }
    /**
     * 找回密码界面
     */
    public void showFoundFragment() {
        setTitle("找回密码");
        if (foundFragment == null) {
            foundFragment = new Foundfragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, foundFragment)
                .commit();
    }
    /**
     * 登录界面
     */
//    public void showLoginFragment() {
//        setTitle("用户登录");
//        if (loginFragment == null)
//            loginFragment = new LoginFragment();
//        slidingMenu.showContent();
//        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, loginFragment).commit();
//
//    }
    /**
     * 用户注册界面
     */
    public void showRegisterFragment() {
        setTitle("用户注册");
        if (registerFragment == null) {
            registerFragment = new RegisterFragment();
        }
        slidingMenu.showContent();
        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, registerFragment)
                .commit();
    }
    //错误的
//    /**
//     * 用户评论界面
//     */
//    public void showCommenetFragment() {
//        setTitle("评论");
//        if (commentFragment == null) {
//            commentFragment = new CommentFragment();
//        }
//        slidingMenu.showContent();
//        getSupportFragmentManager().beginTransaction().replace(R.id.contianer, commentFragment)
//                .commit();
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_left:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showMenu();
                }
                break;
            case R.id.iv_home_right:
                if (slidingMenu != null && slidingMenu.isMenuShowing()) {
                    slidingMenu.showContent();
                } else if (slidingMenu != null) {
                    slidingMenu.showSecondaryMenu();
                }
                break;
        }
    }

    public void changeFragmentStatus() {
        ((SlidingRightFragment) slidingRightFragment).changeView();
    }

    /**
     * 设置标题栏中显示的内容
     *
     * @param name
     */
    public void setTitle(String name) {
//        if(null == tv_home_title){
//            tv_home_title = (TextView) findViewById(R.id.tv_home_title);
//            Log.i(TAG, "setTitle: ==================我是title为空时=================");
//        }
        tv_home_title.setText(name);
    }
}
