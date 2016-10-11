package com.example.administrator.joyapplication.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.MainActivity;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.activity.HomeActivity;
import com.example.administrator.joyapplication.activity.UserActivity;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.model.model.User;
import com.example.administrator.joyapplication.model.model.parser.ParserUser;
import com.example.administrator.joyapplication.model.model.Register;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.example.administrator.joyapplication.util.ShareUtil;

import org.json.JSONObject;

import java.util.List;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btn_login_register, btn_login_login;
    private TextView tv_tofindpwd;
    private EditText et_loginName, et_loginPwd;
    private RequestQueue requestQueue;
    private static final String TAG = "LoginFragment";
    private User user;
    private PopupWindow popupWindow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        btn_login_register = (Button) view.findViewById(R.id.btn_login_register);
        btn_login_login = (Button) view.findViewById(R.id.btn_login_login);
        et_loginName = (EditText) view.findViewById(R.id.et_loginName);

        et_loginPwd = (EditText) view.findViewById(R.id.et_loginPwd);
        tv_tofindpwd = (TextView) view.findViewById(R.id.tv_tofindpwd);
        requestQueue = Volley.newRequestQueue(getActivity());

        btn_login_register.setOnClickListener(this);
        btn_login_login.setOnClickListener(this);
        tv_tofindpwd.setOnClickListener(this);

        return view;
    }

    /**
     * 好像在Fragment中不可以使用popupWindow,先不使用
     */
    public void showPopupWindow() {

//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_findpwd, null);//这个是在网上找的，不能用
//        View view = getLayoutInflater().inflate(R.layout.layout_findpwd, null);//这个是在activity中写的
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_findpwd, null);//这个是在activity中写的
        TextView tv1 = (TextView) view.findViewById(R.id.tv_findpwd);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT,true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).showFoundFragment();
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_register:
                ((HomeActivity) getActivity()).showRegisterFragment();
                break;
            case R.id.btn_login_login:
                String name = et_loginName.getText().toString();
                String pwd = et_loginPwd.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!CommonUtil.verifyPassword(pwd)) {
                    Toast.makeText(getActivity(), "请输入6-16位字符", Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = API.USER_LOGIN + "ver=" + Contacts.VER + "&uid=" + name + "&pwd=" +
                        pwd + "&device=" + "0";
                requestLogin(url);
                break;
            case R.id.tv_tofindpwd:
                showPopupWindow();
//                ((HomeActivity)getActivity()).showFoundFragment();
                break;
        }
    }

    private void requestLogin(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> baseLogin = ParserUser.getRegisterInfo(jsonObject.toString());
                        BaseEntry<User> baseUser= ParserUser.getLoginSuccInfo(jsonObject.toString());
                        user = ParserUser.getLoginSuccInfoss(jsonObject.toString());

                        int result = baseLogin.getStatus();
                        if (result == -1) {
                            showPopupWindow();
                            Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                        if (result == 0) {
                            int loginID = baseLogin.getStatus();
                            if(loginID==0) {
                                Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                                ShareUtil.saveRegisterInfo(baseLogin,getContext());
//                                ShareUtil.saveUserInfo(baseUser,getContext());
                                startActivity(new Intent(getActivity(), UserActivity.class));
                            }
                        }
                        Log.i(TAG, "onResponse: -----------------" + jsonObject);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        requestQueue.add(jsonObjectRequest);//经常忘记写

    }
}
