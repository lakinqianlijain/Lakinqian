package com.qlj.lakinqiandemo.contralayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/8/9.
 */

public class SecondActivity extends AppCompatActivity {
    private LinearLayoutManager mLayoutManager;
    private BaseAdapter mAdapter;
    private SwipeRefreshRecycler mSwipeRefreshLayout;
    private Handler mHandler = new Handler();
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contralayout_second);
        mToolbar= findViewById(R.id.toolbar);
        mToolbar.setTitle("林俊杰");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        mAppBarLayout = findViewById(R.id.app_bar_layout);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mSwipeRefreshLayout.setRefreshable(verticalOffset == 0);
            }
        });
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
        mAdapter.setData(30);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
