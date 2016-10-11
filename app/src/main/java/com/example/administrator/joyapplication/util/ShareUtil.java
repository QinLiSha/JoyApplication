package com.example.administrator.joyapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.Comment;
import com.example.administrator.joyapplication.model.model.Register;
import com.example.administrator.joyapplication.model.model.User;

/**
 * Created by Administrator on 2016/9/5.
 */
public class ShareUtil {
    private static final String SHARED_PATH = "app_share";
    private static final String SHARED_PATH_REGISTER = "register";

    /**
     * 保存用户的注册信息
     *
     * @param baseRegister
     * @param context
     */
    public static void saveRegisterInfo(BaseEntry<Register> baseRegister, Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference_register(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("isLogin", true);
        Register register = baseRegister.getData();
        edit.putInt("result", register.getResult());
        edit.putString("explain", register.getExplain());
        edit.putString("token", register.getToken());
        edit.commit();
    }

    /**
     * 保存用户的登录信息
     *
     * @param user
     * @param context
     */
    public static void saveUserInfo(User user, Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("uid", user.getUid());
        edit.putString("portrait", user.getPortrait());
        edit.commit();

    }
    public static void saveLogin(Context context){
        SharedPreferences preferences = getDefaultSharedPreference_register(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogin", true);
        editor.commit();
    }
    /**
     * 清除数据
     * @param context
     */
    public static void ClearData(Context context) {

        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor logineditor = sharedPreferences.edit();
        logineditor.clear();
        logineditor.commit();

        SharedPreferences sharedPreferences_register = getDefaultSharedPreference_register(context);
        SharedPreferences.Editor regitereditor = sharedPreferences_register.edit();
        regitereditor.clear();
        regitereditor.commit();



    }

    /**
     * 获得uid用户名
     *
     * @param context
     * @return
     */
    public static String getUserUid(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        return sharedPreferences.getString("uid", null);
    }

    /**
     * 获得头像链接
     *
     * @return
     */
    public static String getUserPortrait(Context context) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        return sharedPreferences.getString("portrait", null);
    }

    /**
     * 判断用户是否登录
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getIsLogined(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference_register(context);
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 获得用户的注册信息
     *
     * @param context
     * @param key
     * @return
     */
    public static String getTokey(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference_register(context);
        return sharedPreferences.getString(key, null);
    }

    /**
     * 获得用户的注册信息
     *
     * @param context
     * @return
     */
    public static SharedPreferences getDefaultSharedPreference_register(Context context) {
        return context.getSharedPreferences(SHARED_PATH_REGISTER, context.MODE_PRIVATE);
    }

    /**
     * 获得用户的登录信息
     *
     * @param context
     * @return
     */

    public static SharedPreferences getDefaultSharedPreference(Context context) {
        return context.getSharedPreferences(SHARED_PATH, context.MODE_PRIVATE);
    }
    /** 保存第三方登录用户名*/
    public static void saveUserName(Context context, String userName){
        SharedPreferences preferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid",userName);
        editor.commit();
    }

    /** 保存第三方登录头像 */
    public static void saveUserIcon(Context context, String userIcon){
        SharedPreferences preferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("portrait",userIcon);
        editor.commit();
    }

    /** 保存第三方登录状态存储 */
    public static void saveThreeLogin(Context context, boolean isThreeLogin){
        SharedPreferences preferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isthreelogin", isThreeLogin);
        editor.commit();
    }
    /** 判断第三方登录状态 */
    public static boolean getIsThreeLogin(Context context){
        SharedPreferences loginPreferences = getDefaultSharedPreference(context);
        return loginPreferences.getBoolean("isthreelogin", false);
    }
    /********************************************************************************************************************
     **************************************************************************************************************************************/


    public static void putInt(Context context, String key, int value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }


    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        return sharedPreferences.getString(key, null);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sharedPreferences = getDefaultSharedPreference(context);
        return sharedPreferences.getBoolean(key, defValue);
    }
}
