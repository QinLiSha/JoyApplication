package com.example.administrator.joyapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.MainActivity;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.adapter.LoginAdapter;
import com.example.administrator.joyapplication.base.MyBaseActivity;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.parser.ParserUser;
import com.example.administrator.joyapplication.model.model.User;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.example.administrator.joyapplication.util.MyImageLoader;
import com.example.administrator.joyapplication.util.ShareUtil;

import org.json.JSONObject;

public class UserActivity extends MyBaseActivity {
    private RequestQueue requestQueue;
    private ImageView iv_portrait, iv_back;
    private TextView name, intergral, account_phone_icon, comment_count;
    private static final String TAG = "UserActivity";
    private LoginAdapter adapter;
    private ListView listView;
    private Button btn_exit;
    private boolean isThreeLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        initData();
    }

    private void initView() {
        requestQueue = Volley.newRequestQueue(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_portrait = (ImageView) findViewById(R.id.iv_user_head_icon);
        name = (TextView) findViewById(R.id.tv_user_loginName);
        intergral = (TextView) findViewById(R.id.tv_integral);
        account_phone_icon = (TextView) findViewById(R.id.account_phone_icon);
        comment_count = (TextView) findViewById((R.id.comment_count));
        listView = (ListView) findViewById(R.id.list);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        adapter = new LoginAdapter(this);
        listView.setAdapter(adapter);
        iv_back.setOnClickListener(onClickListener);
        btn_exit.setOnClickListener(onClickListener);

    }


    private void initData() {
        isThreeLogin = ShareUtil.getIsThreeLogin(this);
        if (isThreeLogin) {
            new MyImageLoader(this).display(ShareUtil.getUserPortrait(this), iv_portrait);
            name.setText(ShareUtil.getUserUid(this));
        }else{
            requestUserData();
        }
    }
    private void requestUserData(){
        String token = ShareUtil.getTokey(this, "token");
        String url = API.USER_CENTER_DATA + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI
                (this) + "&token=" + token;
        Log.i(TAG, "onCreate: =============================" + url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                BaseEntry<User> userBaseEntry = ParserUser.getLoginSuccInfo(jsonObject.toString());
                int status = userBaseEntry.getStatus();
                if (status != 0) {
                    showToast("用户数据请求错误!");
                } else {
                    User user = userBaseEntry.getData();
                    ShareUtil.saveUserInfo(user,getBaseContext());
                    Log.i(TAG, "onResponse: ------------------------"+user);
                    name.setText(user.getUid());
                    intergral.setText("用户积分:" + user.getIntegration());
                    comment_count.setText(user.getComnum() + "");
                    adapter.appendDated(user.getLoginlog(), true);
                    adapter.updateAdapter();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if (((keyCode == KeyEvent.KEYCODE_BACK) ||
//                (keyCode == KeyEvent.KEYCODE_HOME))
//                && event.getRepeatCount() == 0) {
//            dialog_Exit(UserActivity.this);
//        }
//        return false;
//
//        //end onKeyDown
//    }

    public void dialog_Exit(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定要退出账号吗?@^w^@");
        builder.setTitle("友情提示:");
        builder.setIcon(R.drawable.exit);
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openActivity(HomeActivity.class);
                        ShareUtil.ClearData(UserActivity.this);
                        finish();
                    }
                });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.iv_back:
                    openActivity(HomeActivity.class);
                    break;
                case R.id.btn_exit:
                    dialog_Exit(UserActivity.this);
                    break;
            }
        }
    };
}
