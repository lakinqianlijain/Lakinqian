package com.qlj.lakinqiandemo.views.lottie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/9/15.
 */

public class LottieActivity extends BaseActivity implements View.OnClickListener {
    private LottieAnimationView mLottieAnimationView;
    private Button mAndroidWave, mFail, mHamburger, mHired,
            mLogo, mLogo1, mLogo2, mWaveLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        initViews();
    }

    private void initViews() {
        mAndroidWave = findViewById(R.id.bt_android_wave);
        mAndroidWave.setOnClickListener(this);
        mFail = findViewById(R.id.bt_fail);
        mFail.setOnClickListener(this);
        mHamburger = findViewById(R.id.bt_hamburger_arrow);
        mHamburger.setOnClickListener(this);
        mHired = findViewById(R.id.bt_hired);
        mHired.setOnClickListener(this);
        mLogo = findViewById(R.id.bt_lottie_logo);
        mLogo.setOnClickListener(this);
        mLogo1 = findViewById(R.id.bt_lottie_logo1);
        mLogo1.setOnClickListener(this);
        mLogo2 = findViewById(R.id.bt_lottie_logo2);
        mLogo2.setOnClickListener(this);
        mWaveLoading = findViewById(R.id.bt_material_wave);
        mWaveLoading.setOnClickListener(this);
        mLottieAnimationView = findViewById(R.id.lottie_view);
        mLottieAnimationView.setOnClickListener(this);
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
    public void onClick(View v) {
        if (mLottieAnimationView.isAnimating()) {
            mLottieAnimationView.cancelAnimation();
        }
        switch (v.getId()) {
            case R.id.bt_android_wave:
                mLottieAnimationView.setAnimation("android_wave.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_fail:
                mLottieAnimationView.setAnimation("image.json");
                mLottieAnimationView.setImageAssetsFolder("image/");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_hamburger_arrow:
                mLottieAnimationView.setAnimation("hamburger_arrow.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_hired:
                mLottieAnimationView.setAnimation("hired.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_lottie_logo:
                mLottieAnimationView.setAnimation("lottie_logo.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_lottie_logo1:
                mLottieAnimationView.setAnimation("lottie_logo1.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_lottie_logo2:
                mLottieAnimationView.setAnimation("lottie_logo2.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.bt_material_wave:
                mLottieAnimationView.setAnimation("material_wave_loading.json");
                mLottieAnimationView.playAnimation();
                break;
            case R.id.lottie_view:
                mLottieAnimationView.setAnimation("android_wave.json");
                mLottieAnimationView.playAnimation();
                mLottieAnimationView.playAnimation();
                break;

        }
    }
}
