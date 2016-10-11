package com.example.administrator.joyapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.adapter.CommentListAdapter;
import com.example.administrator.joyapplication.base.MyBaseActivity;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.BaseEntry;
import com.example.administrator.joyapplication.model.model.Comment;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.model.model.Register;
import com.example.administrator.joyapplication.model.model.parser.ParserNews;
import com.example.administrator.joyapplication.model.model.parser.ParserUser;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.example.administrator.joyapplication.util.ShareUtil;
import com.example.administrator.joyapplication.util.dbutil.DBTools;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class CommentsActivity extends MyBaseActivity {
    private CommentListAdapter commentListAdapter;
    private ListView listView;
    private RequestQueue requestQueue;
    private static final String TAG = "CommentsActivity";
    private List<Comment> commentList;
    private ImageView iv_comment_submit, iv_back;
    private EditText et_comment_content;


    private DBTools dbTools;
    private News news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        init();
        sendRequestList();
    }

    private void init() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_comment_submit = (ImageView) findViewById(R.id.iv_comment_submit);
        et_comment_content = (EditText) findViewById(R.id.et_comment_content);
        listView = (ListView) findViewById(R.id.commentListView);

        commentListAdapter = new CommentListAdapter(this);
        Bundle bundle = getIntent().getExtras();
        news = (News) bundle.getSerializable("news");
        //上面2行是反序列化得到对象
        listView.setAdapter(commentListAdapter);
        dbTools = new DBTools(this);
        requestQueue = Volley.newRequestQueue(this);//实例化一个RequestQueue对象
        iv_back.setOnClickListener(onClickListener);
        iv_comment_submit.setOnClickListener(onClickListener);

    }


    /**
     * 发表评论
     */
    private void sendRequestCommenet(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                BaseEntry<Register> baseLogin = ParserUser.getRegisterInfo(jsonObject.toString());
                int result = baseLogin.getStatus();
                if (result == 0) {
                    showToast("已评论成功!!!");
                    et_comment_content.setText(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    /**
     * 获得评论列表
     */
    private void sendRequestList() {
        String url = API.COMMENTS_DATA + "ver=" + Contacts.VER +
                "&nid=" + news.getNid() + "&type=1" + "&stamp=" + CommonUtil.getSystime() +
                "&cid=20&dir=1&cnt=20";
        Log.i(TAG, "onCreate: ===========我是comment的URL==========" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                commentList = ParserNews.getCommentList(jsonObject.toString());
                commentListAdapter.appendDated(commentList, true);
                commentListAdapter.updateAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("news", news);
                    openActivity(NewsShowActivity.class, bundle);
                    finish();
                    break;
                case R.id.iv_comment_submit:
                    String token = ShareUtil.getTokey(CommentsActivity.this, "token");
                    String comment = et_comment_content.getText().toString();
                    boolean isLogin = ShareUtil.getIsLogined(CommentsActivity.this, "isLogin",
                            false);

                    try {
                        String ss = URLEncoder.encode(comment, "utf-8");//这样手机也可以将汉字显示出来
                        String url = API.COMMENTS_CONTENT + "ver=1&nid=" + news.getNid()
                                + "&token=" + token + "&imei=" + CommonUtil.getIMEI(CommentsActivity
                                .this) + "&ctx=" + ss;

                        if (ss.length() <= 0) {
                            showToast("评论不能为空=@^w^@=");
                        } else {
                            Log.i(TAG, "onClick: ============comment============" + comment);
                            Log.i(TAG, "onClick: ============ss============" + ss);
                            sendRequestCommenet(url);
                            showToast("评论成功=@^w^@=");
                            sendRequestList();
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    };
}
