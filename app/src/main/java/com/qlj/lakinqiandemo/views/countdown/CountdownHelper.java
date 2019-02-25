package com.qlj.lakinqiandemo.views.countdown;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * Created by lakinqian on 2019/1/14.
 */

public class CountdownHelper implements Serializable{
    private static final int REFRESH_PROGRESS = 0x10;

    private CountdownView mCountdownView;
    private long mStartTime, mTotalTime;
    private ValueAnimator mAnimator;
//    private Handler mHandler;

    private Handler mHandler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    mStartTime = mStartTime + 1000;
                    mCountdownView.setProgress(mStartTime, mTotalTime);
                    mHandler2.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
                    break;

                default:
                    break;
            }
            return false;
        }
    });

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_PROGRESS:
                    mStartTime = mStartTime + 1000;
                    mCountdownView.setProgress(mStartTime, mTotalTime);
                    mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
                    break;

                default:
                    break;
            }
        }
    };

//    private Handler.Callback mCallback = new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case REFRESH_PROGRESS:
//                    mStartTime = mStartTime + 1000;
//                    mCountdownView.setProgress(mStartTime, mTotalTime);
//                    mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
//                    break;
//
//                default:
//                    break;
//            }
//            return true;
//        }
//    };
//    private Handler mHandler = new WeakRefHandler(mCallback);
//
//    public class WeakRefHandler extends Handler {
//        private WeakReference<Callback> mWeakReference;
//
//        public WeakRefHandler(Callback callback) {
//            mWeakReference = new WeakReference<Handler.Callback>(callback);
//        }
//
//        public WeakRefHandler(Callback callback, Looper looper) {
//            super(looper);
//            mWeakReference = new WeakReference<Handler.Callback>(callback);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (mWeakReference != null && mWeakReference.get() != null) {
//                Callback callback = mWeakReference.get();
//                callback.handleMessage(msg);
//            }
//        }
//    }

    public void startCountdown(long pastTime, long totalTime, CountdownView countdownView) {
        mStartTime = pastTime;
        mTotalTime = totalTime;
        mCountdownView = countdownView;
        mHandler= new MyHandler(this);
        mAnimator = ValueAnimator.ofInt((int)mStartTime, (int)mTotalTime);
        start();
    }

    private void start() {
//        mCountdownView.setProgress(mStartTime, mTotalTime);
//        mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                if (progress - mStartTime > 1000){
                    mCountdownView.setProgress(progress, mTotalTime);
                    mStartTime = progress;
                    Log.e("66666", "start: 22222" +progress);
                }
            }
        });
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(mTotalTime - mStartTime);
        LinearInterpolator l = new LinearInterpolator();
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setFrameDelay(1000);
        mAnimator.start();
    }


    public void cancel() {
        if (mHandler != null) {
            mHandler.removeMessages(REFRESH_PROGRESS);
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<CountdownHelper> mHelper;

        public MyHandler(CountdownHelper helper) {
            mHelper = new WeakReference<CountdownHelper>(helper);
        }

        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            Log.e("66666", "handleMessage: 22222" );
            CountdownHelper helper = mHelper.get();
            if (helper != null) {
                Log.e("66666", "handleMessage: 11111" );
                switch (msg.what){
                    case REFRESH_PROGRESS:
                        helper.mStartTime = helper.mStartTime + 1000;
                        Log.e("6666", "handleMessage: "+ helper.mStartTime+ "-----");
                        helper.mCountdownView.setProgress(helper.mStartTime, helper.mTotalTime);
                        helper.mHandler.sendEmptyMessageDelayed(REFRESH_PROGRESS, 1000);
                        break;

                    default:
                        break;
                }
            } else {
                Log.e("6666", "handleMessage: 为空" );
            }
        }
    }
}
