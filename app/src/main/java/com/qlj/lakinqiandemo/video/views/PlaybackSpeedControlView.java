package com.qlj.lakinqiandemo.video.views;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;

public class PlaybackSpeedControlView extends RelativeLayout {

    public static final int LAYOUT_ID = R.layout.view_video_speed;
    private Context mContext;
    private View mRootView;
    private OnDismissListener mListener;
    private int mCurrentProgress;
    private TextView mCurrentText;
    private AppCompatSeekBar mSeekBar;

    public PlaybackSpeedControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public PlaybackSpeedControlView setListener(OnDismissListener listener) {
        mListener = listener;
        return this;
    }

    public void show() {
        setVisibility(VISIBLE);
        setOnClickListener(null);
        if (mRootView != null) return;
        mRootView = LayoutInflater.from(mContext).inflate(LAYOUT_ID, this);
        mCurrentText = mRootView.findViewById(R.id.tv_speed);
        mSeekBar = mRootView.findViewById(R.id.sb_speed);
        mSeekBar.setMax(175);
        mCurrentProgress = 75;
        mSeekBar.setProgress(mCurrentProgress);
        mCurrentText.setText(getPlaybackSpeed() + "x");
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCurrentText.setText((float) (progress + 25) / 100 + "x");
                mCurrentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mCurrentProgress = seekBar.getProgress();
                mCurrentText.setText(getPlaybackSpeed() + "x");
            }
        });
        mRootView.findViewById(R.id.iv_plus).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentProgress >= 175) return;
                mCurrentProgress = mCurrentProgress + 5;
                mCurrentText.setText(getPlaybackSpeed() + "x");
                mSeekBar.setProgress(mCurrentProgress);
            }
        });

        mRootView.findViewById(R.id.iv_minus).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentProgress <= 0) return;
                mCurrentProgress = mCurrentProgress - 5;
                mCurrentText.setText(getPlaybackSpeed() + "x");
                mSeekBar.setProgress(mCurrentProgress);
            }
        });
    }

    public void dismiss() {
        setVisibility(GONE);
        if (mListener != null) {
            mListener.onDismiss(getPlaybackSpeed());
        }
    }

    private float getPlaybackSpeed() {
        return (float) (mCurrentProgress + 25) / 100;
    }

    public void reset() {
        if (mRootView == null) return;
        mCurrentProgress = 75;
        mSeekBar.setProgress(mCurrentProgress);
        mCurrentText.setText(getPlaybackSpeed() + "x");
    }

    public interface OnDismissListener {
        void onDismiss(float value);
    }
}
