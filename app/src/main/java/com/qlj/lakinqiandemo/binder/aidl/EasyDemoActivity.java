package com.qlj.lakinqiandemo.binder.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.lakinqian.serverofaidl.IEasyService;
import com.qlj.lakinqiandemo.R;

public class EasyDemoActivity extends AppCompatActivity {

    private static final String TAG = "EasyDemoActivity";
    private static final String ACTION = "com.example.lakinqian.serverofaidl.IEasyService";

    private IEasyService mIEasyService;


    ServiceConnection mServiceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIEasyService = IEasyService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIEasyService=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_demo);
    }



    public void  onButtonClick(View v) throws RemoteException {
        switch (v.getId()){
            case R.id.btn_contact:
                Log.e(TAG,"onButtonClick:   btn_contact=");
                if(mIEasyService!=null){
                    mIEasyService.connect(" Cilent connect");
                }

                break;
            case R.id.btn_start_service:
                Log.e(TAG,"onButtonClick:   btn_start_service=");
                Intent intent = new Intent(ACTION);
                intent.setPackage("com.example.lakinqian.serverofaidl");
                bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                if(mIEasyService!=null){
                    mIEasyService.disConnect(" Cilent disconnect");
                    unbindService(mServiceConnection);
                }
                break;
        }
    }
}
