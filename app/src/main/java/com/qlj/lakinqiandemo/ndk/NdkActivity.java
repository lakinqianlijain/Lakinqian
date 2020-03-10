package com.qlj.lakinqiandemo.ndk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

public class NdkActivity extends BaseActivity implements View.OnClickListener {
    private Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);
        initViews();
    }

    private void initViews() {
        mButton = findViewById(R.id.bt_ndk_study);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.bt_ndk_study:
                String text = NdkTools.getStringFromNDK();
                Toast.makeText(this, "通过NDK获取对应的String:"+text, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
