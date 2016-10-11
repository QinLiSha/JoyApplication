package com.example.administrator.joyapplication.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.joyapplication.R;


/**
 * Created by Administrator on 2016/9/5.
 */
public class MyBaseActivity<T> extends FragmentActivity {
    private static final String TAG = "MyBaseActivity";
    private Toast toast;
    private static int screenW, screenH;//手机屏幕的宽和高
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        screenW = getWindowManager().getDefaultDisplay().getWidth();
        screenH = getWindowManager().getDefaultDisplay().getWidth();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * =========================
     * 公共功能的封装
     * =========================
     */
    public void showToast(String msg) {
        if (toast == null)
          toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();

    }

    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, null);
    }
    public void openActivity(Class<?> pClass, Bundle bundle) {
    openActivity(pClass, bundle, null);
    }
    public void openActivity(Class<?> pClass, Bundle bundle, Uri uri) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null)
            intent.putExtras(bundle);
        if (uri != null)
            intent.setData(uri);
        startActivity(intent);
        //增加动画
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_botton_out);
    }

    public void showLoadingDialog(Context context, String msg, boolean cancel) {
        View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);//加载一个自定义的布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_dialog_view);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_loading_img);
        TextView tv = (TextView) view.findViewById(R.id.tv_loading_msg);
        Animation roatAnim = AnimationUtils.loadAnimation(context, R.anim.loading_animation);//设置一个动画
        iv.setAnimation(roatAnim);
        if (null != msg) {
            tv.setText(msg);
        }
        dialog = new Dialog(context, R.style.loading_dialog);//设置dialog的样式
        dialog.setContentView(layout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();

    }

}
