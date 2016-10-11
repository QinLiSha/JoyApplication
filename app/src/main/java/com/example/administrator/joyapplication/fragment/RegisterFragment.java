package com.example.administrator.joyapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.activity.HomeActivity;
import com.example.administrator.joyapplication.activity.UserActivity;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.parser.ParserUser;
import com.example.administrator.joyapplication.model.model.Register;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.example.administrator.joyapplication.util.ShareUtil;

import org.json.JSONObject;

public class RegisterFragment extends Fragment {
    private EditText editText_email, editText_name, editText_pwd;
    private CheckBox cb_agree_agreement;
    private Button btn_register_immediately;
    private RequestQueue requestQueue;
    private static final String TAG = "RegisterFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        editText_email = (EditText) view.findViewById(R.id.editText_email);
        editText_name = (EditText) view.findViewById(R.id.editText_name);
        editText_pwd = (EditText) view.findViewById(R.id.editText_pwd);
        cb_agree_agreement = (CheckBox) view.findViewById(R.id.cb_agree_agreement);
        btn_register_immediately = (Button) view.findViewById(R.id.btn_register_immediately);
        requestQueue = Volley.newRequestQueue(getActivity());
        btn_register_immediately.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String name, pwd, email;
            email = editText_email.getText().toString();
            name = editText_name.getText().toString();
            pwd = editText_pwd.getText().toString();
            if (!cb_agree_agreement.isChecked()) {
                Toast.makeText(getActivity(), "请仔细阅读条款,并选择同意", Toast.LENGTH_LONG).show();
                return;
            }
            if (!CommonUtil.verifyEmail(email)) {
                Toast.makeText(getActivity(), "请输入正确的邮箱格式", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_LONG).show();
                return;
            }
            if (!CommonUtil.verifyPassword(pwd)) {
                Toast.makeText(getActivity(), "请输入6-16位字母和数字的组合", Toast.LENGTH_LONG).show();
                return;
            }
            String url = API.USER_REGISTER + "ver=" + Contacts.VER + "&uid=" + name + "&email=" +
                    email + "&pwd=" + pwd;
            Log.i(TAG, "onClick: ==============" + url);
            requestResiger(url);
        }
    };

    private void requestResiger(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        BaseEntry<Register> baseRegister = ParserUser.getRegisterInfo(jsonObject.toString());
                        Register register = baseRegister.getData();
                        int result = register.getResult();
                        if (result == -2) {
                            Toast.makeText(getActivity(), "用户名重复", Toast.LENGTH_LONG).show();
                        }
                        if (result == -3) {
                            Toast.makeText(getActivity(), "邮箱重复", Toast.LENGTH_LONG).show();
                        }
                        if (result == 0) {
                            startActivity(new Intent(getActivity(), UserActivity.class));
                            ShareUtil.saveRegisterInfo(baseRegister,getContext());
                            ((HomeActivity)getContext()).changeFragmentStatus();
                        }
                        Log.i(TAG, "onResponse: -----------------" + jsonObject);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }
                );
        requestQueue.add(jsonObjectRequest);//经常忘记写
    }
}
