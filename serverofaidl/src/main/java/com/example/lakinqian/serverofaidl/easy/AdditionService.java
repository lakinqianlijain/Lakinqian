package com.example.lakinqian.serverofaidl.easy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.lakinqian.serverofaidl.IAdd;

import androidx.annotation.Nullable;

public class AdditionService extends Service {

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.e("6666", "onBind:   intent = " + intent.toString());
		return mBinder;
	}

	private final IAdd.Stub mBinder = new IAdd.Stub() {

		@Override
		public int addNumbers(int num1, int num2) {
			return num1 + num2;
		}

	};

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Log.e("6666", "onStart:   =");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.e("6666", "onUnbind:   =");
		return super.onUnbind(intent);

	}

	@Override
	public void onDestroy() {
		Log.e("6666", "onDestroy:   =");
		super.onDestroy();
	}
}