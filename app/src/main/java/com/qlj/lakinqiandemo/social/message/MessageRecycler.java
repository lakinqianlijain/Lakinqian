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
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

import java.util.ArrayList;
import java.util.List;

import static com.qlj.lakinqiandemo.social.message.DynamicMessageActivity.DYNAMIC_LIKE_COMMENT;
import static com.qlj.lakinqiandemo.social.message.DynamicMessageActivity.USER_FOLLOW;

/**
 * Created by lakinqian on 2019/2/28.
 */

public class MessageRecycler extends RecyclerView implements View.OnClickListener {
    private LayoutInflater mLayoutInflater;
    private MessageAdapter mAdapter;
    private List<TestBean> mDataList = new ArrayList<>();

    public MessageRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(getContext());
        mAdapter = new MessageAdapter();
        this.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        this.setLayoutManager(layoutManager);
    }

    public void setData(List<TestBean> beans) {
        mDataList.clear();
        mDataList.addAll(beans);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_message_panel:
                TestBean testBean = (TestBean) v.getTag();
                enterDetailPage(testBean);
                break;
        }
    }

    private void enterDetailPage(TestBean testBean) {
        if (testBean.getType() == 1) {
            JumpActivityUtil.startDynamicMessageActivity(getContext(), DynamicMessageActivity.class, DYNAMIC_LIKE_COMMENT);
        } else if (testBean.getType() == 2) {
            JumpActivityUtil.startDynamicMessageActivity(getContext(), DynamicMessageActivity.class, USER_FOLLOW);
        } else {
            JumpActivityUtil.JumpSelfActivity(getContext(), SessionActivity.class);
        }
    }

    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {
        private static final int LAYOUT_ID = R.layout.item_soc_message;

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(LAYOUT_ID, null);
            return new MessageHolder(view);
        }

        @Override
        public void onBindViewHolder(MessageHolder holder, int position) {
            TestBean testBean = mDataList.get(position);
            holder.itemView.setTag(testBean);
            holder.mUserNameText.setText(testBean.getNickname());
            holder.mMessageText.setText(testBean.getContent());
            holder.mMessageTimeText.setText(testBean.getSendTime());
            if (testBean.getNoReadCount() > 0) {
                holder.mNoRead.setVisibility(VISIBLE);
                holder.mNoRead.setBackground(JianApplication.get().getResources().getDrawable(R.drawable.bg_point_red));
                holder.mNoRead.setText(testBean.getNoReadCount() + "");
                holder.mNoRead.setTextColor(JianApplication.get().getResources().getColor(R.color.arrow_color));
            }
            Glide.with(getContext()).load(testBean.getAvatar())
                    .transform(new GlideRoundTransform(getContext()))
                    .placeholder(R.drawable.ic_video_default_avatar)
                    .centerCrop().into(holder.mAvatarImage);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }

    private class MessageHolder extends ViewHolder {
        private ImageView mAvatarImage;
        private TextView mUserNameText, mMessageText, mMessageTimeText, mNoRead;

        public MessageHolder(View itemView) {
            super(itemView);
            mAvatarImage = itemView.findViewById(R.id.iv_avatar);
            mUserNameText = itemView.findViewById(R.id.tv_user_name);
            mMessageText = itemView.findViewById(R.id.tv_message);
            mMessageTimeText = itemView.findViewById(R.id.tv_message_time);
            mNoRead = itemView.findViewById(R.id.tv_related_text);
            itemView.setOnClickListener(MessageRecycler.this);
        }
    }
}
