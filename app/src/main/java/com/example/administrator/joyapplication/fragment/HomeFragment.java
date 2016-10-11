package com.example.administrator.joyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.activity.HomeActivity;
import com.example.administrator.joyapplication.activity.NewsShowActivity;
import com.example.administrator.joyapplication.adapter.HomeListViewAdapter;
import com.example.administrator.joyapplication.adapter.NewTypeAdapter;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.gloable.API;
import com.example.administrator.joyapplication.gloable.Contacts;
import com.example.administrator.joyapplication.model.model.NewsType;
import com.example.administrator.joyapplication.model.model.parser.ParserNews;
import com.example.administrator.joyapplication.model.model.SubType;
import com.example.administrator.joyapplication.util.CommonUtil;
import com.example.administrator.joyapplication.util.dbutil.DBTools;
import com.example.administrator.joyapplication.view.HorizontalListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Volley框架
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private HorizontalListView horizontalListView;
    private RequestQueue requestQueue;
    private NewTypeAdapter newTypeAdapter;
    private HomeListViewAdapter newListAdapter;
    private ListView listView;
    private List<NewsType> newsTypeList;
    private List<News> newsList;
    private List<SubType> subTypeList;
    private DBTools dbTools;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        horizontalListView = (HorizontalListView) view.findViewById(R.id.horizontalView);
        newTypeAdapter = new NewTypeAdapter(getContext());
        horizontalListView.setAdapter(newTypeAdapter);
        listView = (ListView) view.findViewById(R.id.listView_home);
        newListAdapter = new HomeListViewAdapter(getContext());
        listView.setAdapter(newListAdapter);

        requestQueue = Volley.newRequestQueue(getContext());//实例化一个RequestQueue对象
        dbTools = new DBTools(getContext());
        loadTitleTypeData();
        loadHomeList();
//        sendRequestList();//本来不是写在一个里面,应该通过横向listview来显示,但是,网站不是这样配置的,不能实现
        listView.setOnItemClickListener(onItemClickListener);
        return view;
    }

    /**
     * 加载标题栏数据
     */
    private void loadTitleTypeData() {
        if (CommonUtil.isNetworkAvilable(getActivity())) {
            sendRequestData();
        } else {
            List<SubType> list = dbTools.getLocalType();
            if (list != null && list.size() > 0) {
                newTypeAdapter.appendDated(list, true);
                newTypeAdapter.updateAdapter();
            } else {
                ((HomeActivity) getActivity()).showToast("请检查当前的网络状态!!!");

            }

        }
    }

    /**
     * 加载首页数据
     */
    private void loadHomeList() {
        if (CommonUtil.isNetworkAvilable(getActivity())) {
            sendRequestList();
        } else {
            newsList = dbTools.getLocalNews();
            if (newsList != null && newsList.size() > 0) {
                newListAdapter.appendDated(newsList, true);
                newListAdapter.updateAdapter();
            } else {
                ((HomeActivity) getActivity()).showToast("请检查当前的网络状态!!");
            }
        }
    }

    /**
     * 加标题栏数据
     */
    private void sendRequestData() {
        String URL = API.NEWS_SORT + "ver=" + Contacts.VER + "&imei=" + CommonUtil.getIMEI
                (getContext());
        Log.i(TAG, "onResponse: ======我是输出标题栏URL=======" + URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i(TAG, "onResponse: ======我是输出标题栏jsonObject=======" + jsonObject);

                newsTypeList = ParserNews.getNewsType(jsonObject.toString());
                subTypeList = new ArrayList<>();
                for (int i = 0; i < newsTypeList.size(); i++) {
                   subTypeList.addAll(newsTypeList.get(i).getSubgrp());
                }
                for (int i = 0; i < subTypeList.size(); i++) {
                    dbTools.saveLocalSubType(subTypeList.get(i));
                }
                newTypeAdapter.appendDated(subTypeList, true);
                newTypeAdapter.updateAdapter();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }


    private void sendRequestList() {
        // 解析:http://118.244.212.82:9092/newsClient/news_list?ver=1dsf&subid=1&dir=1&nid=0&stamp
        // =20160828&cnt=20
        String url = API.NEWS_LIST + "ver=" + Contacts.VER + "dsf&subid=" + 1 + "&dir=1" +
                "&nid=2" + "&stamp=" + CommonUtil.getSystime() + "&cnt=2";
        Log.i(TAG, "sendRequestList: ===============我是输出新闻列表======================" + url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                newsList = ParserNews.getNewList(jsonObject.toString());
                for (int i = 0; i < newsList.size(); i++) {
                    dbTools.saveLocalNews(newsList.get(i));
                }
                newListAdapter.appendDated(newsList, true);
                newListAdapter.updateAdapter();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView
            .OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            News news = newListAdapter.getItem(position);
            bundle.putSerializable("news", news);
            //上面几行是序列化以便传输
            ((HomeActivity) getActivity()).openActivity(NewsShowActivity.class, bundle);
        }
    };
}
