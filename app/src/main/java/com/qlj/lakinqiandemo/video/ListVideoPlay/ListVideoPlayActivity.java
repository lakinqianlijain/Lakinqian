package com.qlj.lakinqiandemo.video.ListVideoPlay;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/25.
 */

public class ListVideoPlayActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private LinearLayoutManager mLayoutManager;
    private ListVideoAdapter mAdapter;
    String url = "http://mp4.vjshi.com/2016-12-22/e54d476ad49891bd1adda49280a20692.mp4";
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

    private class ListVideoAdapter extends RecyclerView.Adapter {
        private Context mContext;
        private List<Object> mDataList = new ArrayList<>();
        public ListVideoAdapter(Context context) {
            mContext = context;
        }

        public void setData(List<Object> videos){
            mDataList.addAll(videos);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView;
            int layoutId = R.layout.list_video_play_item;
            itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            return new ListVideoHolder(itemView, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Object item = mDataList.get(position);
            if (holder instanceof ListVideoHolder){
                ((ListVideoHolder)holder).videoItem.setTag(mDataList.get(position));
                ((ListVideoHolder)holder).videoPlay.setTag(mDataList.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        private class ListVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private View videoItem;
            private ImageView videoPlay;
            private SimpleExoPlayerView exoPlayerView;
            public ListVideoHolder(View itemView, int viewType) {
                super(itemView);
                videoItem = itemView.findViewById(R.id.video_play_item);
                videoPlay = itemView.findViewById(R.id.iv_play);
                exoPlayerView = itemView.findViewById(R.id.player);
                videoItem.setOnClickListener(this);
                videoPlay.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.video_play_item:
                        String url = (String) v.getTag();
                        playVideo(url);
                        break;
                    case R.id.iv_play:
                        String url1 = (String) v.getTag();
                        playVideo(url1);
                        break;
                }
            }

            private void playVideo(String url) {
                ListVideoPlayerHelper listVideoPlayerHelper = new ListVideoPlayerHelper(ListVideoPlayActivity.this, exoPlayerView);
                listVideoPlayerHelper.playUrl(url, "mp4", true);
            }
        }
    }
}
