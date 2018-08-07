package com.qlj.lakinqiandemo.contralayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/8/4.
 */

public class FirstActivity extends BaseActivity {
    private RelativeLayout mTitlePanel;
    private LinearLayout mSearchView;
    private LinearLayoutManager mLayoutManager;
    private BaseAdapter mAdapter;
    private SwipeRefreshRecycler mSwipeRefreshLayout;
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contralayout_first);
        initView();
    }

    private void initView() {
        mTitlePanel = findViewById(R.id.title_panel);
        mSearchView = findViewById(R.id.ll_search_view);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshRecycler.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mSwipeRefreshLayout.setLayoutManager(mLayoutManager);
        mAdapter = new BaseAdapter(this);
        mSwipeRefreshLayout.setAdapter(mAdapter);
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
