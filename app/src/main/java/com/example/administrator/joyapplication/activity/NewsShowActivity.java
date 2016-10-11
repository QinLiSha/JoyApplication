package com.example.administrator.joyapplication.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.base.MyBaseActivity;
import com.example.administrator.joyapplication.fragment.HomeFragment;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.model.model.User;
import com.example.administrator.joyapplication.model.model.parser.ParserNews;
import com.example.administrator.joyapplication.model.model.parser.ParserUser;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.example.administrator.joyapplication.util.dbutil.DBTools;

import org.json.JSONObject;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsShowActivity extends MyBaseActivity {
    private WebView webView;
    private TextView textView;
    private ProgressBar progressBar;
    private PopupWindow popupWindow;
    private ImageView imageView, iv_back;
    private DBTools dbTools;
    private News news;
    private RequestQueue requestQueue;
    private LinearLayout ll_commentNum;
    private static final String TAG = "NewsShowActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CommonUtil.isNetworkAvilable(this)) {
            setContentView(R.layout.no_network);
        } else {
            requestQueue = Volley.newRequestQueue(this);
            setContentView(R.layout.activity_news_show);
            init();
//            ShareSDK.initSDK(this, "sharesdk的appkey");

        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
//        oks.setTitle("标题");
        oks.setTitle(news.getTitle());
// titleUrl是标题的网络链接，QQ和QQ空间等使用
//        oks.setTitleUrl("http://sharesdk.cn");
        oks.setTitleUrl(news.getLink());
// text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
        oks.setText(news.getSummary());
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");
        oks.setUrl(news.getIcon());
        Log.i(TAG, "showShare: -------------------getIcon-----------------"+news.getIcon());
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(news.getLink());

// 启动分享GUI
        oks.show(this);
    }

    private void init() {

            webView = (WebView) findViewById(R.id.myWebView);
            progressBar = (ProgressBar) findViewById(R.id.pb_progress_list);
            imageView = (ImageView) findViewById(R.id.iv_show_menu);
            iv_back = (ImageView) findViewById(R.id.iv_back);
            textView = (TextView) findViewById(R.id.tv_commentsNum);
            ll_commentNum = (LinearLayout) findViewById(R.id.ll_commentNum);

            Bundle bundle = getIntent().getExtras();
            news = (News) bundle.getSerializable("news");
            imageView.setOnClickListener(onClickListener);
            iv_back.setOnClickListener(onClickListener);
            ll_commentNum.setOnClickListener(onClickListener);

            dbTools = new DBTools(this);
            //上面2行是反序列化得到对象
            requestCommentData();
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
// User settings

            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setUseWideViewPort(true);//关键点

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            webSettings.setDisplayZoomControls(false);
//            webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
            webSettings.setAllowFileAccess(true); // 允许访问文件
            webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
            webSettings.setSupportZoom(true); // 支持缩放
            webSettings.setLoadWithOverviewMode(true);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int mDensity = metrics.densityDpi;
            Log.d("maomao", "densityDpi = " + mDensity);
            if (mDensity == 240) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == 160) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            } else if (mDensity == 120) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
            } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else if (mDensity == DisplayMetrics.DENSITY_TV) {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            } else {
                webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            }

            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            ;
            WebChromeClient client = new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progressBar.setProgress(newProgress);
                    if (newProgress >= 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            };
            webView.setWebChromeClient(client);
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webView.loadUrl(news.getLink());
/**
 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
 */
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);;
//            webView.getSettings().setLoadWithOverviewMode(true);
            webView.loadUrl(news.getLink());
            showPopupWindow();
        }

    private void showPopupWindow() {
        View view = getLayoutInflater().inflate(R.layout.layout_favorite, null);
        TextView tv1 = (TextView) view.findViewById(R.id.tv_faorites);
        TextView tv2 = (TextView) view.findViewById(R.id.tv_shareNews);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup
                .LayoutParams.WRAP_CONTENT);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                boolean isSavad = dbTools.saveLocalFavorite(news);
                Log.i(TAG, "onClick: =======__++===========" + isSavad);
                if (isSavad) {
                    showToast("收藏成功^-^");
                } else {
                    showToast("收藏夹内已存在,无需再次添加^-^!");
                }
                List<News> list = dbTools.getLocalFavorite();
                Log.i(TAG, "onClick: =========我是收藏夹内的listview==========" + list);

            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Toast.makeText(getApplicationContext(),"分享",Toast.LENGTH_LONG).show();
                showShare();
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    openActivity(HomeActivity.class);
                    finish();
                    break;
                case R.id.iv_show_menu:
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    } else if (popupWindow != null) {
                        popupWindow.showAsDropDown(imageView, 0, 20);//最后一个是对距离网页标题栏的设置
                    }
                    break;
                case R.id.ll_commentNum:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", news);
                    openActivity(CommentsActivity.class, bundle);
                    break;
            }

        }
    };


    private void requestCommentData() {
        final String url = API.COMMENTS_NUMBER + "ver=" + Contacts.VER + "&nid=" + news.getNid();
        Log.i(TAG, "requestCommentData: ================我是跟帖地址=================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                int commentNum = ParserNews.getCommentNum(jsonObject.toString());
                Log.i(TAG, "requestCommentData: ================我是跟帖数=================" +
                        commentNum);

                if (commentNum <= 0) {
                    ll_commentNum.setVisibility(View.INVISIBLE);
                } else {
                    textView.setText(commentNum + "");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
