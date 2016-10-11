package com.example.administrator.joyapplication.model.model.parser;

import com.example.administrator.joyapplication.fragment.FavoritesFragment;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.ForgetPassword;
import com.example.administrator.joyapplication.model.model.Register;
import com.example.administrator.joyapplication.model.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ParserUser {
    /**
     * 解析用户注册信息
     *
     * @param json
     * @return
     */
    public static BaseEntry<Register> getRegisterInfo(String json) {
        Gson gson = new Gson();
        Type type;
        type = new TypeToken<BaseEntry<Register>>() {
        }.getType();
        BaseEntry<Register> registerBaseEntry = gson.fromJson(json, type);
        return registerBaseEntry;
    }

    public static BaseEntry<User> getLoginSuccInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<User> userBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<User>>() {
        }.getType());
        return userBaseEntry;
    }

    public static User getLoginSuccInfoss(String json) {
        Gson gson = new Gson();
        BaseEntry<User> userBaseEntry = gson.fromJson(json, new TypeToken<BaseEntry<User>>() {
        }.getType());
        User user = userBaseEntry.getData();
        return user;
    }

    public static BaseEntry<ForgetPassword> getemailBoxInfo(String json) {
        Gson gson = new Gson();
        BaseEntry<ForgetPassword> baseEntry = gson.fromJson(json, new
                TypeToken<BaseEntry<ForgetPassword>>() {
        }.getType());
        return baseEntry;
    }
}
