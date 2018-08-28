package com.qlj.lakinqiandemo.video.ListVideoPlay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.video.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/8/28.
 */

public class ListVideoAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private List<Object> mDataList = new ArrayList<>();
    private ListVideoPlayerHelper mVideoPlayHelper;

    public ListVideoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<Object> videos) {
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
        if (holder instanceof ListVideoHolder) {
            ((ListVideoHolder) holder).videoItem.setTag(mDataList.get(position));
            ((ListVideoHolder) holder).videoPlay.setTag(mDataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private void playVideo(String url, SimpleExoPlayerView exoPlayerView,IListVideoItemView listVideoItemView ) {
        if (mVideoPlayHelper == null) {
            mVideoPlayHelper = new ListVideoPlayerHelper(mContext, listVideoItemView);
        }
        mVideoPlayHelper.setPlayView(exoPlayerView);
        mVideoPlayHelper.playUrl(url, "mp4", true);
    }

    public void playerRelease() {
        mVideoPlayHelper.releaseVideoPlayer();
    }

    private class ListVideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener,IListVideoItemView {
        private View videoItem;
        private ImageView videoPlay;
        private SimpleExoPlayerView exoPlayerView;
        private RelativeLayout mPlayer;
        private ProgressBar mProgressBar;

        public ListVideoHolder(View itemView, int viewType) {
            super(itemView);
            videoItem = itemView.findViewById(R.id.video_play_item);
            videoPlay = itemView.findViewById(R.id.iv_play);
            exoPlayerView = itemView.findViewById(R.id.player);
            videoItem.setOnClickListener(this);
            videoPlay.setOnClickListener(this);
            mPlayer = itemView.findViewById(R.id.rl_player);
            mProgressBar = itemView.findViewById(R.id.pb_loading);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.video_play_item:
                case R.id.iv_play:
                    mPlayer.setVisibility(View.VISIBLE);
                    String url = (String) v.getTag();
                    playVideo(url, exoPlayerView, this);
                    break;
            }
        }

        @Override
        public void onStartLoadVideo() {

        }

        @Override
        public void onLoadVideoSuccess() {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
