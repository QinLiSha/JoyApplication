package com.example.administrator.joyapplication.model.model.parser;

import android.util.Log;

import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.UpdateVerify;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ParserUpdateVerify {


    private static final String TAG = "ParserUpdate";

    public static BaseEntry<UpdateVerify> getUpdateVerify(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<BaseEntry<UpdateVerify>>() {

        }.getType();
        BaseEntry<UpdateVerify> entity = gson.fromJson(json, type);

        Log.i(TAG, "getNewsType: -----------" + entity);
        return entity;
    }

}
