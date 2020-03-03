package com.example.lakinqian.serverofaidl.easy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.lakinqian.serverofaidl.IEasyService;

public class EasyService extends Service {

    private static final String TAG = "EasyService";

    public EasyService() {
    }

    IEasyService.Stub mIBinder = new IEasyService.Stub() {
        @Override
        public void connect(String mes) throws RemoteException {
            Log.e("6666", "我是来自aidl服务端的log: connect——》"+mes);
        }

        @Override
        public void disConnect(String mes) throws RemoteException {
            Log.e("6666", "我是来自aidl服务端的log: disConnect——》" + mes);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind:   intent = " + intent.toString());
        return mIBinder;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "onStart:   =");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind:   =");
        return super.onUnbind(intent);

    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy:   =");
        super.onDestroy();
    }
}
