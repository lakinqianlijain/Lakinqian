package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.FormatUtil;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;
import com.qlj.lakinqiandemo.views.CircleProgressBar;
import com.qlj.lakinqiandemo.views.ExpandableTextView;
import com.qlj.lakinqiandemo.views.MyToast;
import com.qlj.lakinqiandemo.views.ScrollingHintView;

import java.util.ArrayList;
import java.util.List;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.GESTURE_PASSWORD;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.PIN_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.ENTER_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.PinContentViewDot.SET_PASSWORD;

/**
 * Created by Administrator on 2018/8/12.
 */

public class CustomizeViewActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mPin, mGesture;
    private TextView mToast, mStopCircleProgress;
    private ScrollingHintView mScrollingView;
    private CircleProgressBar mCircleProgress;
    private ValueAnimator mAnimator;
    private ExpandableTextView mExpandView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_view);
        initView();
        initData();
    }

    private void initView() {
        mPin = findViewById(R.id.set_pin);
        mPin.setOnClickListener(this);
        mGesture = findViewById(R.id.set_gesture);
        mGesture.setOnClickListener(this);
        mToast = findViewById(R.id.tv_my_toast);
        mToast.setOnClickListener(this);
        mScrollingView = findViewById(R.id.shv_notice_tips);
        findViewById(R.id.tv_start_circle_progress).setOnClickListener(this);
        mStopCircleProgress = findViewById(R.id.tv_stop_circle_progress);
        mStopCircleProgress.setOnClickListener(this);
        mCircleProgress = findViewById(R.id.cpb_circle_progress);
        mExpandView = findViewById(R.id.etv_expandable_view);
        mExpandView.setText(BuildStrategyTips());
        ((TextView) mExpandView.findViewById(R.id.expandable_text)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initData() {
        mScrollingView.startWithList(getStringList());
    }

    private List<CharSequence> getStringList() {
        List<CharSequence> list = new ArrayList<>();
        SpannableString ss1 = new SpannableString("Dave got a football and a book，and a bag.");
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss1);
        SpannableString ss2 = new SpannableString("Dave got a football and a book，and a bag.");
        ss2.setSpan(new ForegroundColorSpan(Color.GREEN), 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss2);
        SpannableString ss3 = new SpannableString("Dave got a football and a book，and a bag.");
        ss3.setSpan(new URLSpan("http://sunfusheng.com/"), 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss3);
        list.add("Dave got a football and a book，and a bag.");
        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_pin:
                String pin = SharedPreferenceUtil.readString(this, DEMO_CONFIG, PIN_PASSWORD, "");
                if (TextUtils.isEmpty(pin)) {
                    JumpActivityUtil.JumpLockActivity(this, PinEditActivity.class, SET_PASSWORD);
                } else {
                    JumpActivityUtil.JumpLockActivity(this, PinEditActivity.class, ENTER_PASSWORD);
                }
                break;
            case R.id.set_gesture:
                String gesture = SharedPreferenceUtil.readString(this, DEMO_CONFIG, GESTURE_PASSWORD, "");
                if (TextUtils.isEmpty(gesture)) {
                    JumpActivityUtil.JumpLockActivity(this, GestureEditActivity.class, SET_PASSWORD);
                } else {
                    JumpActivityUtil.JumpLockActivity(this, GestureEditActivity.class, ENTER_PASSWORD);
                }
                break;
            case R.id.tv_my_toast:
                MyToast myToast = new MyToast(R.layout.toast_layout, this, Toast.LENGTH_SHORT);
                myToast.show();
                break;
            case R.id.tv_start_circle_progress:
                simulateProgress();
                break;
            case R.id.tv_stop_circle_progress:
                if (mAnimator == null) return;
                if (!mAnimator.isPaused()) {
                    mAnimator.pause();
                    mStopCircleProgress.setText("Restart");
                } else {
                    mAnimator.resume();
                    mStopCircleProgress.setText("Stop");
                }
                break;
        }
    }

    private void simulateProgress() {
        mAnimator = ValueAnimator.ofInt(0, 100);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                mCircleProgress.setProgress(progress);
            }
        });
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(30000);
        mAnimator.start();
    }

    private SpannableStringBuilder BuildStrategyTips() {
        Resources res = getResources();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString income_guide1 = new SpannableString(res.getString(R.string.text_income_guide_1));
        AbsoluteSizeSpan spanGuide = new AbsoluteSizeSpan(16, true);
        income_guide1.setSpan(spanGuide, 0, income_guide1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(income_guide1);
        spannableStringBuilder.append("\n\n");
        spannableStringBuilder.append(res.getString(R.string.text_income_guide_2));
        spannableStringBuilder.append("\n\n");
        String string = res.getString(R.string.text_income_guide_3);
        SpannableString coinMall = new SpannableString(res.getString(R.string.text_gold_coin_mall));
        String[] formatStrings = FormatUtil.setSpannableString(string, coinMall.toString());
        spannableStringBuilder.append(formatStrings[0]);

        coinMall.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                JumpActivityUtil.JumpLockActivity(CustomizeViewActivity.this, PinEditActivity.class, SET_PASSWORD);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.color_policy_text));
                ds.setUnderlineText(true);
            }
        }, 0, coinMall.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(coinMall);
        spannableStringBuilder.append(formatStrings[1]);

        return spannableStringBuilder;
    }

}
