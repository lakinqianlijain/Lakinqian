package com.qlj.lakinqiandemo.contralayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/8/4.
 */

public class FirstActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private RelativeLayout mTitlePanel;
    private LinearLayout mSearchView;
    private LinearLayoutManager mLayoutManager;
    private BaseAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contralayout_first);
        initView();
    }

    private void initView() {
        mRecycler = findViewById(R.id.folder_list);
        mTitlePanel = findViewById(R.id.title_panel);
        mSearchView = findViewById(R.id.ll_search_view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter = new BaseAdapter(this);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setData(50);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
