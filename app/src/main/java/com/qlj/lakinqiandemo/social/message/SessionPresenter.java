package com.qlj.lakinqiandemo.social.message;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.social.Utils;
import com.qlj.lakinqiandemo.social.bean.TestMessageBean;
import com.qlj.lakinqiandemo.social.emoticonLib.MoonUtils;

import java.util.List;

/**
 * Created by lakinqian on 2019/3/7.
 */

public class SessionPresenter {
    private ISessionView mISessionView;
    private List<TestMessageBean> mData;
    private SessionAdapter mAdapter;
    Handler mHandler = new Handler();

    public SessionPresenter (ISessionView sessionView){
        mISessionView = sessionView;
    }

    public void setSessionData(){
        mData = Utils.getConversationData();
        mAdapter = new SessionAdapter(mISessionView.getContext());
        mAdapter.setData(mData);
        mISessionView.getContentRecycler().setAdapter(mAdapter);
        rvMoveToBottom();
    }

    public void sedMessage(){
        MoonUtils.replaceEmoticons(JianApplication.get(), mISessionView.getEtContent().getText(), 0, mISessionView.getEtContent().getText().toString().length());
        final String message = mISessionView.getEtContent().getText().toString();
        if (TextUtils.isEmpty(message)){
            return;
        }
        mISessionView.getEtContent().setText("");
        TestMessageBean messageBean = new TestMessageBean(0, message, Utils.getMine(), "09:00", 1);
        mAdapter.addData(messageBean);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TestMessageBean messageBean = new TestMessageBean(1, "bibi get : "+message, Utils.getMine(), "09:00", 1);
                mAdapter.addData(messageBean);
                rvMoveToBottom();
            }
        }, 300);
        rvMoveToBottom();
    }

    private void rvMoveToBottom() {
        mISessionView.getContentRecycler().smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }
}
