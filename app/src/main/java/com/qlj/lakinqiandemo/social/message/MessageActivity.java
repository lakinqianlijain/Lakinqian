package com.qlj.lakinqiandemo.social.message;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.StandardTitleActivity;
import com.qlj.lakinqiandemo.social.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2019/2/28.
 */

public class MessageActivity extends StandardTitleActivity {
    private static final int LAYOUT_ID = R.layout.activity_soc_message;
    private MessageRecycler mMessageRecycler;
    private List<TestBean> mTestBeans = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT_ID);
        setTitleText("Messages");
        initViews();
        initData();
    }

    @Override
    public void onTitleBackPressed() {
        finish();
    }

    private void initData() {
        TestBean testBean = new TestBean(1, "", "Like and comment", "bibi like this post", "2017/03/02", 1);
        TestBean testBean1 = new TestBean(2, "", "Follow", "bibi follow you,you are friends now", "2017/03/02", 2);
        TestBean testBean2 = new TestBean(3, "", "Warning", "Dear bibi, like this post", "2017/03/02", 3);
        TestBean testBean3 = new TestBean(4, "", "lakin", "hi,where you are", "07:54", 3);
        TestBean testBean4 = new TestBean(4, "", "Jams", "hi, my name is bibi", "09:26", 4);
        mTestBeans.add(testBean);
        mTestBeans.add(testBean1);
        mTestBeans.add(testBean2);
        mTestBeans.add(testBean3);
        mTestBeans.add(testBean4);
        mMessageRecycler.setData(mTestBeans);

    }

    private void initViews() {
        mMessageRecycler = findViewById(R.id.mr_message_list);
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
