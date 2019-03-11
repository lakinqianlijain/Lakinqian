package com.qlj.lakinqiandemo.social.message;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.StandardTitleActivity;
import com.qlj.lakinqiandemo.social.Utils;
import com.qlj.lakinqiandemo.social.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2019/3/1.
 */

public class DynamicMessageActivity extends StandardTitleActivity {
    private static final int LAYOUT_ID = R.layout.activity_soc_dynamic;
    public static final String DYNAMIC_TYPE = "dynamic_type";
    public static final int DYNAMIC_LIKE_COMMENT = 0;
    public static final int USER_FOLLOW = 1;

    private MessageDetailRecycler mMessageDetailRecycler;
    private List<TestBean> mTestBeans = new ArrayList<>();
    private int mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
        mType = getIntent().getIntExtra(DYNAMIC_TYPE, 0);
        setTitleWithType();
        initViews();
        initData();
    }

    private void setTitleWithType() {
        if (mType == DYNAMIC_LIKE_COMMENT) {
            setTitleText("Like and comment");
        } else if (mType == USER_FOLLOW) {
            setTitleText("Follow");
        }
    }

    private void initViews() {
        mMessageDetailRecycler = findViewById(R.id.mr_dynamic_message_list);
    }

    private void initData() {
        if (mType == DYNAMIC_LIKE_COMMENT) {
            mTestBeans = Utils.getCommentMessageData();
        } else {
            mTestBeans = Utils.getFollowMessageData();
        }
        mMessageDetailRecycler.setData(mTestBeans);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTitleBackPressed() {
        finish();
    }
}
