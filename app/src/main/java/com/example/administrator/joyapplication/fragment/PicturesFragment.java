package com.example.administrator.joyapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.example.administrator.joyapplication.R;
import com.example.administrator.joyapplication.adapter.HomeListViewAdapter;
import com.example.administrator.joyapplication.adapter.PictureAdapter;
import com.example.administrator.joyapplication.model.model.News;
import com.example.administrator.joyapplication.util.dbutil.DBTools;

import java.util.List;


public class PicturesFragment extends Fragment {
    private GridView gridView;
    private PictureAdapter adapter;
    private DBTools dbTools;
    private static final String TAG = "PicturesFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictures, container, false);
        gridView = (GridView) view.findViewById(R.id.gv_pic);
        adapter = new PictureAdapter(getActivity());
        dbTools = new DBTools(getContext());
        gridView.setAdapter(adapter);//特别容易忘
        getData();

        return view;
    }


    public void getData() {
        List<News> list = dbTools.getLocalNews();
        adapter.appendDated(list, true);
        adapter.updateAdapter();
        Log.i(TAG, "getData: ============list=============="+list);
    }
}
