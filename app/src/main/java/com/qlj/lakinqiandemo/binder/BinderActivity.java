package com.qlj.lakinqiandemo.binder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lakinqian.serverofaidl.IAdd;
import com.example.lakinqian.serverofaidl.IEasyService;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.binder.aidl.EasyDemoActivity;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;

public class BinderActivity extends BaseActivity implements View.OnClickListener {
    Button mAidl1, mAidl2, mBinder;
    private IAdd mIadd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_activity);
        initView();
        Intent intent = new Intent("com.example.lakinqian.serverofaidl.AdditionService");
        intent.setPackage("com.example.lakinqian.serverofaidl");
        boolean is = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        mAidl1 = findViewById(R.id.bt_aidl1);
        mAidl1.setOnClickListener(this);
        mAidl2 = findViewById(R.id.bt_aidl2);
        mAidl2.setOnClickListener(this);
        mBinder = findViewById(R.id.bt_binder);
        mBinder.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_aidl1:
                JumpActivityUtil.JumpSelfActivity(this, EasyDemoActivity.class);
                break;
            case R.id.bt_aidl2:
                mAidl2.setText("34+56");
                AddByAidl();
                break;
            case R.id.bt_binder:
                break;

        }
    }

    private void AddByAidl() {
        if (mIadd == null) return;
        try {
            int num = mIadd.addNumbers(34, 56);
            mAidl2.setText(String.valueOf(num));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIadd = IAdd.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIadd = null;
        }
    };
}
