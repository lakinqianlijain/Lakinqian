package com.qlj.lakinqiandemo.social.code;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.code.common.QRCodeView;
import com.code.common.ZXingView;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.PermissionUtil;

/**
 * Created by lakinqian on 2019/3/12.
 */

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    public static final int RC_PERMISSION_CAMERA = 1001;
    private ZXingView mZXingView;
    private TextView mResult;
    private TextView mFlashControl;
    private boolean mFlashOpen;
    private long mCurrentTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_scan_qr_code);
        initView();
        initPermission();
    }

    private void initPermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if (!PermissionUtil.hasPermissions(this, perms)) {
            Log.e("6666", "initPermission: ");
            requestPermission();
        }
    }

    private void requestPermission() {
        try {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA},
                    RC_PERMISSION_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mResult = findViewById(R.id.tv_scan_result);
        mZXingView = findViewById(R.id.zv_scan_view);
        mFlashControl = findViewById(R.id.tv_control_flash);
        mFlashControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlashOpen) {
                    mZXingView.closeFlashlight();
                    mFlashOpen = false;
                    mFlashControl.setText("Touch and light up");
                    mFlashControl.setSelected(false);
                    mFlashControl.setVisibility(View.INVISIBLE);
                } else {
                    mZXingView.openFlashlight();
                    mFlashControl.setText("Touch to Close");
                    mFlashOpen = true;
                    mFlashControl.setSelected(true);
                }
            }
        });
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startSpotAndShowRect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RC_PERMISSION_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mZXingView.startSpotAndShowRect();
                }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZXingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mZXingView.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.e("didi", "onScanQRCodeSuccess: " + System.currentTimeMillis());
        mResult.setText(result);
        vibrate();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        Log.e("6666", "onCameraAmbientBrightnessChanged: " + (System.currentTimeMillis() - mCurrentTime));
        mCurrentTime = System.currentTimeMillis();
        if (isDark) {
            mFlashControl.setVisibility(View.VISIBLE);
        } else {
            if (!mFlashOpen && mFlashControl.getVisibility() == View.VISIBLE) {
                mFlashControl.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
