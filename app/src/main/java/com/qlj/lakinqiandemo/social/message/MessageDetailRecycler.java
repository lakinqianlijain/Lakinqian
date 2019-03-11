package com.qlj.lakinqiandemo.social.message;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.social.bean.TestBean;
import com.qlj.lakinqiandemo.social.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2019/3/1.
 */

public class MessageDetailRecycler extends RecyclerView {
    private LayoutInflater mLayoutInflater;
    private MessageDetailAdapter mAdapter;
    private List<TestBean> mDataList = new ArrayList<>();

    public MessageDetailRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(getContext());
        mAdapter = new MessageDetailAdapter();
        this.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.setLayoutManager(layoutManager);
    }

    public void setData(List<TestBean> beans) {
        mDataList.clear();
        mDataList.addAll(beans);
        mAdapter.notifyDataSetChanged();
    }

    private class MessageDetailAdapter extends RecyclerView.Adapter<DynamicMessageHolder> {
        private static final int LAYOUT_ID = R.layout.item_soc_message;

        @Override
        public DynamicMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(LAYOUT_ID, null);
            return new DynamicMessageHolder(view);
        }

        @Override
        public void onBindViewHolder(DynamicMessageHolder holder, int position) {
            TestBean testBean = mDataList.get(position);
            holder.mUserNameText.setText(testBean.getNickname());
            holder.mMessageText.setText(testBean.getContent());
            holder.mMessageTimeText.setText(testBean.getSendTime());
            if (testBean.getType() == 2) {
                holder.mFollowStatus.setVisibility(VISIBLE);
                holder.mFollowStatus.setTextColor(JianApplication.get().getResources().getColor(R.color.progress_text_color));
                holder.mFollowStatus.setText("Follow");
            }
            Glide.with(getContext()).load(testBean.getAvatar())
                    .transform(new GlideRoundTransform(getContext()))
                    .placeholder(R.drawable.ic_video_default_avatar)
                    .into(holder.mAvatarImage);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }

    private class DynamicMessageHolder extends ViewHolder {
        private ImageView mAvatarImage;
        private TextView mUserNameText, mMessageText, mMessageTimeText, mFollowStatus;

        public DynamicMessageHolder(View itemView) {
            super(itemView);
            mAvatarImage = itemView.findViewById(R.id.iv_avatar);
            mUserNameText = itemView.findViewById(R.id.tv_user_name);
            mMessageText = itemView.findViewById(R.id.tv_message);
            mMessageTimeText = itemView.findViewById(R.id.tv_message_time);
            mFollowStatus = itemView.findViewById(R.id.tv_related_text);
        }
    }
}
