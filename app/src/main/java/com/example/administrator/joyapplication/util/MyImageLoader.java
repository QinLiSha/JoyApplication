package com.example.administrator.joyapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 缓存图片
 * 三级缓存!!!!!!!!!!!!!!!!!!!!!!!!!
 * Created by Administrator on 2016/9/9.
 */

public class MyImageLoader {


    private static final String TAG = "MyImageLoader";
    private Context context;
    private static LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>((int) (Runtime
            .getRuntime().freeMemory() / 4));

    public MyImageLoader(Context context) {
        this.context = context;
    }

    /**
     * 加载图片
     * display是展示,陈列的意思
     *
     * @param path
     * @param imageView
     * @return
     */
    public Bitmap display(String path, ImageView imageView) {
        Bitmap bitmap = null;
    if (path == null && path.length() <= 0) {
        return bitmap;
    }
    //先去内存缓存区看看有没有图片
    bitmap = loadImageFromReference(path);
    if (bitmap != null) {
        Log.i(TAG, "display:内存缓存中的数据");
        imageView.setImageBitmap(bitmap);
        return bitmap;
    }
    //从本地的缓存中去取
    bitmap = loadImageFromCache(path);
    if (bitmap != null) {
        mCache.put(path, bitmap);//存入缓存区
        imageView.setImageBitmap(bitmap);
        Log.i(TAG, "display: 本地缓存中的数据");
        return bitmap;
    }

    //从网络中去取图片
    DownPic(path, imageView);
    Log.i(TAG, "display: 网络的数据");
    return bitmap;
}

    private void DownPic(final String path, final ImageView iv) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int code = msg.what;
                if (code == 1) {
                    Bitmap bm = (Bitmap) msg.obj;
                    iv.setImageBitmap(bm);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(path);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream is = con.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);//从网络上加载的图片bitmap
                    //存入缓存
                    mCache.put(path, bitmap);
                    //存入本地
                    saveLocal(path, bitmap);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * 从缓存区去去图片
     * 根据缓存的时候的一个类似于key的路径
     * reference引用,参考的意思
     *
     * @param url
     * @return
     */
    private Bitmap loadImageFromReference(String url) {
        return mCache.get(url);
    }

    /**
     * 从本地去取,也就是SD卡
     *
     * @param url
     * @return
     */
    private Bitmap loadImageFromCache(String url) {
        //http://www.baidu.com/text/hdhk.jpg
        String name = url.substring(url.lastIndexOf("/") + 1);
        if (name != null) {
            return null;
        }
        File cacheDir = context.getExternalCacheDir();//返回一个缓存目录
        if (cacheDir != null) {
            return null;
        }
        File[] files = cacheDir.listFiles();
        if (files == null) {
            return null;
        }
        File bitmapFile = null;
        for (File file : files) {
            if (file.getName().equals(name)) {
                bitmapFile = file;
            }
        }
        if (bitmapFile == null) {
            return null;
        }

        Bitmap fileBitmap = null;
        fileBitmap = BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
        return fileBitmap;
    }

    /**
     * 将图片存入本地SD卡
     *
     * @param url
     */
    private void saveLocal(String url, Bitmap bitmap) {
        String name = url.substring(url.lastIndexOf("/") + 1);
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir != null) {
            cacheDir.mkdir();
        }
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(new File(cacheDir, name));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);     //写入本地
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
