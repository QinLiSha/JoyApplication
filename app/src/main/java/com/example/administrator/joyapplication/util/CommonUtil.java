package com.example.administrator.joyapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CommonUtil {

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getSystime() {
        String systime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        systime = dateFormat.format(new Date(System.currentTimeMillis()));
        return systime;
    }

    /**
     * 获取手机版本
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 验证密码格式
     *
     * @param password
     * @return
     */
    public static boolean verifyPassword(String password) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{6,16}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 判断邮箱的格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean verifyEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))" + "([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 格式的转换
     *
     * @param file
     * @return
     */
    public static String getFileInfo(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuffer strBuff = new StringBuffer();
        if (file < 1024) {
            strBuff.append(file);
            strBuff.append("B");
        } else if (file < (1024 * 1024)) {
            strBuff.append(df.format((double) (file / 1024)));
            strBuff.append("K");
        } else if (file < (1024 * 1024 * 1024)) {
            strBuff.append(df.format((double) (file / 1024 * 1024)));
            strBuff.append("M");
        } else if (file < (1024 * 1024 * 1024 * 1024)) {
            strBuff.append(df.format((double) (file / 1024 * 1024 * 1024)));
            strBuff.append("G");
        }
        return strBuff.toString();
    }

    /**
     * 判断是否有网络连接
     *
     * @param activity
     * @return
     */
    public static boolean isNetworkAvilable(Activity activity) {
        Context context = activity.getApplicationContext();
        //获得手机所有的连接管理对象(包括对wi-fi,net等的连接管理)
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService
                (Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            //获得NetworkInfo对象
            NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
            if (networkInfos != null && networkInfos.length > 0) {
                for (int i = 0, count = networkInfos.length; i < count; i++) {
                    System.out.print(i + "---------状态----" + networkInfos[i].getState());
                    System.out.print(i + "---------类型----" + networkInfos[i].getTypeName());
                    //判断当前网络状态是否为连接状态
                    if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取手机的IMEI值  International Mobile Equipment Identity 国际移动设备标识
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}
