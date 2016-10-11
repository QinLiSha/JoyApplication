package com.example.administrator.joyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.ForgetPassword;
import com.example.administrator.joyapplication.model.model.parser.ParserUser;

import org.json.JSONObject;

public class Foundfragment extends Fragment {
    private Button btn_findpwd;
    private EditText et_mailbox;
    private static final String TAG = "Foundfragment";
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foundfragment, container, false);
        btn_findpwd = (Button) view.findViewById(R.id.btn_findpasswd);
        et_mailbox = (EditText) view.findViewById(R.id.et_mailbox);
        requestQueue = Volley.newRequestQueue(getContext());//实例化一个RequestQueue对象
        btn_findpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestList();
            }
        });
        return view;
    }

    /**
     * 解析:http://118.244.212.82:9092/newsClient/user_forgetpass?ver=1&email=2545115@qq.com
     * {"message":"ERROR","status":0,"data":{"result":-2,"explain":"该邮箱不存在！"}}
     * {"message":"OK","status":0,"data":{"result":0,"explain":"已成功发送到邮箱"}}
     */
    private void sendRequestList() {

        String email = et_mailbox.getText().toString();
        if (email.length() > 0 && email != null) {
            String url = API.USER_FORGET_PASS + "ver=" + Contacts.VER + "&email=" + email;
            Log.i(TAG, "sendRequestList: =====================================" + url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    BaseEntry<ForgetPassword> baseRegister = ParserUser.getemailBoxInfo
                            (jsonObject.toString());
                    ForgetPassword forgetPassword = baseRegister.getData();
                    int result = forgetPassword.getResult();
                    if (result == -2) {
                        Toast.makeText(getActivity(), "该邮箱不存在！", Toast.LENGTH_LONG).show();
                    }
                    if (result == 0) {
                        Toast.makeText(getActivity(), "已成功发送到邮箱", Toast.LENGTH_LONG).show();
                    }
                    Log.i(TAG, "onResponse: -----------------" + jsonObject);
                }
            }, new Response.ErrorListener() {
                @Override

                public void onErrorResponse(VolleyError volleyError) {
                }
            });
            requestQueue.add(jsonObjectRequest);
        } else{
            Toast.makeText(getActivity(), "邮箱不能为空", Toast.LENGTH_LONG).show();
        }
    }


}
