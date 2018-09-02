package com.qlj.lakinqiandemo.video.ListVideoPlay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/25.
 */

public class ListVideoPlayActivity extends BaseActivity{
    private RecyclerView mRecycler;
    private LinearLayoutManager mLayoutManager;
    private ListVideoAdapter mAdapter;
    String url = "http://mp4.vjshi.com/2016-04-05/add12db77c7c5cd6dfef4c1955b36a80.mp4";
    String format = "mp4";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video_play);
        initViews();
    }

    private void initViews() {
        mRecycler = findViewById(R.id.rv_video_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new ListVideoAdapter(this);
        mAdapter.setData(getListVideo());
        mRecycler.setAdapter(mAdapter);
    }

    private List<Object> getListVideo() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            list.add(url);
        }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.playerRelease();
    }
}
