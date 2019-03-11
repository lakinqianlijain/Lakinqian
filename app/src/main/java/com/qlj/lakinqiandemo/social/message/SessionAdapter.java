package com.qlj.lakinqiandemo.social.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.social.bean.TestMessageBean;
import com.qlj.lakinqiandemo.social.emoticonLib.MoonUtils;
import com.qlj.lakinqiandemo.social.utils.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2019/3/6.
 */

public class SessionAdapter extends RecyclerView.Adapter {
    private static final int TYPE_SEND = 1;
    private static final int TYPE_RECEIVER = 2;
    private static final int TYPE_TIME = 3;

    private static final int SEND_MESSAGE = R.layout.item_send_message;
    private static final int RECEIVER_MESSAGE = R.layout.item_receiver_message;
    private static final int MESSAGE_TIME = R.layout.item_message_time;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Object> mDataList = new ArrayList<>();

    public SessionAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<TestMessageBean> messageBeans){
        mDataList.add(new TimeItem(messageBeans.get(0).getSendTime()));
        mDataList.addAll(messageBeans);
        notifyDataSetChanged();
    }

    public void addData(TestMessageBean testMessageBean){
        mDataList.add(testMessageBean);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_SEND) {
            view = mLayoutInflater.inflate(SEND_MESSAGE, parent, false);
            return new SendHolder(view);
        } else if (viewType == TYPE_RECEIVER) {
            view = mLayoutInflater.inflate(RECEIVER_MESSAGE, parent, false);
            return new ReceiverHolder(view);
        } else if (viewType == TYPE_TIME) {
            view = mLayoutInflater.inflate(MESSAGE_TIME, parent, false);
            return new TimeHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        Object object = mDataList.get(position);
        switch (type){
            case TYPE_TIME:
                ((TimeHolder)holder).mSendTime.setText(((TimeItem)object).getTime());
                break;
            case TYPE_SEND:
                TestMessageBean testMessageBean = (TestMessageBean) object;
                SendHolder sendHolder = (SendHolder) holder;
                sendHolder.mContentText.setText(replaceEmoticons(testMessageBean.getContent()));
                Glide.with(mContext).load(testMessageBean.getSendUser().getAvatar())
                        .transform(new GlideRoundTransform(mContext))
                        .placeholder(R.drawable.ic_video_default_avatar).into(sendHolder.mAvatar);
                break;
            case TYPE_RECEIVER:
                TestMessageBean testMessage = (TestMessageBean) object;
                ReceiverHolder receiverHolder = (ReceiverHolder) holder;
                receiverHolder.mContentText.setText(replaceEmoticons(testMessage.getContent()));
                Glide.with(mContext).load(testMessage.getSendUser().getAvatar())
                        .transform(new GlideRoundTransform(mContext))
                        .placeholder(R.drawable.ic_video_default_avatar).into(receiverHolder.mAvatar);
                break;

        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataList.get(position);
        if (item instanceof TimeItem){
            return TYPE_TIME;
        }
        if (item instanceof TestMessageBean){
            if (((TestMessageBean) item).getType() == 0){
                return TYPE_SEND;
            } else {
                return TYPE_RECEIVER;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private class TimeItem {
        String time;
        private TimeItem(String time){
            this.time = time;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    private class SendHolder extends RecyclerView.ViewHolder {
        ImageView mAvatar, mSendFail;
        TextView mContentText;
        ProgressBar mProgressbar;

        private SendHolder(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.iv_avatar);
            mSendFail = itemView.findViewById(R.id.iv_send_fail);
            mContentText = itemView.findViewById(R.id.tv_send_content);
            mProgressbar = itemView.findViewById(R.id.pb_sending);
        }
    }

    public class ReceiverHolder extends RecyclerView.ViewHolder {
        ImageView mAvatar, mSendFail;
        TextView mContentText;
        ProgressBar mProgressbar;

        public ReceiverHolder(View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.iv_avatar);
            mSendFail = itemView.findViewById(R.id.iv_send_fail);
            mContentText = itemView.findViewById(R.id.tv_receiver_content);
            mProgressbar = itemView.findViewById(R.id.pb_sending);
        }
    }

    public class TimeHolder extends RecyclerView.ViewHolder {
        TextView mSendTime;

        public TimeHolder(View itemView) {
            super(itemView);
            mSendTime = itemView.findViewById(R.id.tv_send_ime);
        }
    }

    private Editable replaceEmoticons(String message){
        Editable editable = new SpannableStringBuilder(message);
        MoonUtils.replaceEmoticons(mContext, editable, 0, editable.toString().length());
        return editable;
    }

}
