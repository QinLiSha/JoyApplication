package com.example.administrator.joyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.activity.HomeActivity;
import com.example.administrator.joyapplication.activity.NewsShowActivity;
import com.example.administrator.joyapplication.adapter.HomeListViewAdapter;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.util.dbutil.DBTools;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private ListView listView;
    private HomeListViewAdapter adapter;
    private DBTools dbTools;
    private RelativeLayout rl_noneFavorites;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        listView = (ListView) view.findViewById(R.id.favoritesListView);
        rl_noneFavorites = (RelativeLayout) view.findViewById(R.id.rl_noneFavorites);

        adapter = new HomeListViewAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        dbTools = new DBTools(getContext());
        getData();
        return view;
    }


    public void getData() {
        List<News> list = dbTools.getLocalFavorite();
        adapter.appendDated(list,true);
        adapter.updateAdapter();

        if(list.size()<=0){
            rl_noneFavorites.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else{
            listView.setVisibility(View.VISIBLE);
            rl_noneFavorites.setVisibility(View.GONE);
        }
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            News news = adapter.getItem(position);
            bundle.putSerializable("news",news);
            ((HomeActivity)getActivity()).openActivity(NewsShowActivity.class,bundle);
        }
    };
}
