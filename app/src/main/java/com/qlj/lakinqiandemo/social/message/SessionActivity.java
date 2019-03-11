package com.qlj.lakinqiandemo.social.message;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.StandardTitleActivity;
import com.qlj.lakinqiandemo.social.Utils;
import com.qlj.lakinqiandemo.social.bean.TestMessageBean;
import com.qlj.lakinqiandemo.social.emoticonLib.EmoticonLayout;
import com.qlj.lakinqiandemo.social.emoticonLib.EmotionKeyboard;

import java.util.List;

/**
 * Created by lakinqian on 2019/3/1.
 */

public class SessionActivity extends StandardTitleActivity implements ISessionView{
    private LinearLayout mConversationContent;
    private EditText mEditContent;
    private ImageView mImageEmo;
    private EmoticonLayout mEmotionLayout;
    private EmotionKeyboard mEmotionKeyboard;
    private RecyclerView mRecycler;
    private SessionPresenter mPresenter;
    private Button mSendMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setTitleText("Lakin");
        initView();
        initEmotionKeyboard();
        init();
    }

    private void init() {
        initData();
        initListener();
    }

    private void initListener() {
        mEditContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    mRecycler.smoothScrollToPosition(mRecycler.getAdapter().getItemCount() - 1);
                }
            }
        });

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sedMessage();
            }
        });

        mEmotionKeyboard.setOnEmotionButtonOnClickListener(new EmotionKeyboard.OnEmotionButtonOnClickListener() {
            @Override
            public boolean onEmotionButtonOnClickListener(View view) {
                return false;
            }
        });
    }

    private void initData() {
        mPresenter = new SessionPresenter(this);
        mPresenter.setSessionData();
    }

    private void initView() {
        mRecycler = findViewById(R.id.rv_session_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(layoutManager);
        mConversationContent = findViewById(R.id.ll_conversation_content);
        mEditContent = findViewById(R.id.et_content);
        mImageEmo = findViewById(R.id.iv_emoticon);
        mEmotionLayout = findViewById(R.id.el_emotion_layout);
        mEmotionLayout.attachEditText(mEditContent);
        mSendMessage = findViewById(R.id.bt_send_message);
    }

    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToContent(mConversationContent);
        mEmotionKeyboard.bindToEmotionButton(mImageEmo);
        mEmotionKeyboard.bindToEditText(mEditContent);
        mEmotionKeyboard.setEmotionLayout(mEmotionLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEditContent.clearFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTitleBackPressed() {
        finish();
    }

    @Override
    public RecyclerView getContentRecycler() {
        return mRecycler;
    }

    @Override
    public EditText getEtContent() {
        return mEditContent;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
