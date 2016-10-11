package com.example.administrator.joyapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.activity.HomeActivity;
import com.example.administrator.joyapplication.activity.UserActivity;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.UpdateVerify;
import com.example.administrator.joyapplication.model.model.parser.ParserUpdateVerify;
import com.example.administrator.joyapplication.util.MyImageLoader;
import com.example.administrator.joyapplication.util.ShareUtil;
import com.example.administrator.joyapplication.util.UpdateManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


public class SlidingRightFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_loginImageView, iv_loginedImageView,iv_fun_weixin,iv_qq,iv_fun_friend,iv_fun_weibo;
    private TextView tv_loginTextView, tv_loginedName, tv_update;
    private RelativeLayout layout_unlogin, layout_logined;
    private boolean isLogin;
    private static final String TAG = "SlidingRightFragment";
private Platform plat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sliding_right, container, false);

        layout_unlogin = (RelativeLayout) view.findViewById(R.id.layout_unlogin);
        iv_loginImageView = (ImageView) view.findViewById(R.id.iv_loginImageView);
        tv_loginTextView = (TextView) view.findViewById(R.id.tv_loginTextView);
        layout_logined = (RelativeLayout) view.findViewById(R.id.layout_logined);
        iv_loginedImageView = (ImageView) view.findViewById(R.id.iv_loginedImageView);
        tv_loginedName = (TextView) view.findViewById(R.id.tv_loginedName);
        tv_update = (TextView) view.findViewById(R.id.tv_update);

        iv_fun_weixin = (ImageView) view.findViewById(R.id.iv_fun_weixin);
        iv_qq = (ImageView) view.findViewById(R.id.iv_fun_qq);
        iv_fun_friend = (ImageView) view.findViewById(R.id.iv_fun_friend);
        iv_fun_weibo = (ImageView) view.findViewById(R.id.iv_fun_weibo);


        ShareSDK.initSDK(getContext());//开始时忘记将ShareSDK初始化了,结果软件停止运行
        iv_loginImageView.setOnClickListener(this);
        tv_loginTextView.setOnClickListener(this);
        iv_loginedImageView.setOnClickListener(this);
        tv_loginedName.setOnClickListener(this);
        tv_update.setOnClickListener(this);

        iv_fun_weixin.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_fun_friend.setOnClickListener(this);
        iv_fun_weibo.setOnClickListener(this);

        changeView();
        return view;
    }

    /**
     * 登录状态替换未登录状态(头像和用户名)
     */
    public void changeView() {
        isLogin = ShareUtil.getIsLogined(getActivity(), "isLogin", false);
        if (!isLogin) {
            layout_unlogin.setVisibility(View.VISIBLE);
            layout_logined.setVisibility(View.GONE);
        } else {
            layout_unlogin.setVisibility(View.GONE);
            layout_logined.setVisibility(View.VISIBLE);
            tv_loginedName.setText(ShareUtil.getUserUid(getContext()));
            new MyImageLoader(getContext()).display(ShareUtil.getUserPortrait(getContext()),
                    iv_loginedImageView);
        }
    }

    /**
     * 更新版本
     * @param url
     */
    private void updateApk(String url) {
        UpdateManager updateManager = new UpdateManager(getContext(), url);
        updateManager.checkUpdateInfo();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                BaseEntry<UpdateVerify> baseEntity = ParserUpdateVerify.
                        getUpdateVerify(msg.obj.toString());
                if (baseEntity.getStatus() == 0) {
                    UpdateVerify updateVerify = baseEntity.getData();
                    if (Contacts.VER == updateVerify.getVersion()) {
                        ((HomeActivity) getActivity()).showToast("版本已经是最新的了！");
                    } else {
                        String updateUrl = updateVerify.getLink();
                        updateApk(updateUrl);
                    }
                } else {
                    ((HomeActivity) getActivity()).showToast("获取更新信息失败，请检查网络连接");
                }
            }else{
                ((HomeActivity) getActivity()).showToast("您好,您访问的网站暂时无法连接.......");

            }
        }
    };

    /**
     * 版本更新的请求方法
     */
    private void sendQuesone_updateVerify() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "run: ===============版本更新的请求方法=============");
                    URL url = new URL("http://192.168.3.80:8080/test/update.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection
                            .getInputStream()));
                    StringBuffer stringBuffer = new StringBuffer();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = stringBuffer.toString();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 授权和获得用户数据
     *
     */
//    private void grantAuthorization() {
//        Platform qq = ShareSDK.getPlatform(QQ.NAME);
//        qq.SSOSetting(false);  //设置false表示使用SSO授权方式
//        qq.setPlatformActionListener(new PlatformActionListener() {
//            @Override
//            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                     //用户资源都保存到res
//                    //通过打印res数据看看有哪些数据是你想要的
//                if (i == Platform.ACTION_USER_INFOR) {
//                    PlatformDb platDB = platform.getDb();//获取数平台数据DB
//                    //通过DB获取各种数据
////                    platDB.getToken();
////                    platDB.getUserGender();
//                    platDB.getUserIcon();
//                    platDB.getUserName();
//                    String name = qq.getDb().getUserName();
//                    String icon = qq.getDb().getUserIcon();
//                    ShareUtil.saveThreeLogin(getContext(), true);
//                    ShareUtil.saveLogin(getContext());
//                    ((HomeActivity) getActivity()).openActivity(UserActivity.class);
//
//                }
//            }
//            @Override
//            public void onError(Platform platform, int i, Throwable throwable) {
//
//            }
//            @Override
//            public void onCancel(Platform platform, int i) {
//
//            }
//        }); // 设置分享事件回调
//        qq.authorize();//单独授权
//
//}

    private void loginAuthorize(String platformName) {
        if (plat ==null)
            plat = ShareSDK.getPlatform(platformName);
        plat.removeAccount();
        plat.setPlatformActionListener(actionListener);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        plat.showUser(null);//授权并获取用户信息
    }

    private PlatformActionListener actionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            String name = plat.getDb().getUserName();
            String icon = plat.getDb().getUserIcon();
            ShareUtil.saveUserName(getContext(), name);
            ShareUtil.saveUserIcon(getContext(), icon);
            ShareUtil.saveThreeLogin(getContext(), true);
            ShareUtil.saveLogin(getContext());
            ((HomeActivity) getActivity()).openActivity(UserActivity.class);
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    };

    /**
     * 设置点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        //当点击的是头像或者是文字时跳转到登录界面
//        if (v.getId() == R.id.iv_loginImageView || v.getId() == R.id.tv_loginTextView) {
//            ((HomeActivity) getActivity()).showLoginFragment();
//        }
//        if (v.getId() == R.id.iv_loginedImageView || v.getId() == R.id.tv_loginedName) {
//            startActivity(new Intent(getActivity(), UserActivity.class));
//        }
//        if (v.getId() == R.id.tv_update) {
//            Log.i(TAG, "onClick: -------------版本更新点击事件---------------");
//            sendQuesone_updateVerify();
//        }
//        if (v.getId()==R.id.iv_fun_qq){
//            Toast.makeText(getActivity(),"qq正在登陆:@^w^@:",Toast.LENGTH_LONG).show();
//            Log.i(TAG, "onClick: ---------QQ-点击事件---------");
////            ((HomeActivity) getActivity()).showToast("我是qq！");
//            loginAuthorize(QQ.NAME); }

            //当用户点击的是头像或文字时，直接跳转登录界面
            switch (v.getId()) {
                case R.id.iv_loginImageView:
                case R.id.tv_loginTextView:
                    ((HomeActivity) getActivity()).showLoginFragment();
                    break;
                case R.id.iv_loginedImageView:
                case R.id.tv_loginedName:
                    ((HomeActivity) getActivity()).openActivity(UserActivity.class);
                    break;
                case R.id.iv_fun_weixin:
//                    sendQuesone_updateVerify();
                    loginAuthorize(QQ.NAME);
                    break;
                case R.id.iv_fun_qq:
                    Toast.makeText(getActivity(),"qq正在登陆:@^w^@:",Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onClick: ---------QQ-点击事件---------");
                    loginAuthorize(QQ.NAME);
                    break;
                case R.id.iv_fun_friend:
                    loginAuthorize(QQ.NAME);
                    break;
                case R.id.iv_fun_weibo:
                    loginAuthorize(QQ.NAME);
                    break;

        }
    }
}
