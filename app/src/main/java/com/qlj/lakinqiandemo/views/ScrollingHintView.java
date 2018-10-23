package com.qlj.lakinqiandemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/10/23.
 */

public class ScrollingHintView extends ViewFlipper {

    private boolean mSingleLine = false;
    private int mTextSize = 14;
    private int mTextColor = 0xffffffff;
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;
    private int mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;

    private boolean isAnimStart = false;
    private int mPosition;

    private List<? extends CharSequence> mNotices = new ArrayList<>();


    public ScrollingHintView(Context context) {
        super(context);
    }

    public ScrollingHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollingHintViewStyle, defStyleAttr, 0);

        mSingleLine = typedArray.getBoolean(R.styleable.ScrollingHintViewStyle_shvSingleLine, false);
        if (typedArray.hasValue(R.styleable.ScrollingHintViewStyle_shvTextSize)) {
            mTextSize = (int) typedArray.getDimension(R.styleable.ScrollingHintViewStyle_shvTextSize, mTextSize);
            mTextSize = DensityUtil.px2sp(context, mTextSize);
        }
        mTextColor = typedArray.getColor(R.styleable.ScrollingHintViewStyle_shvTextColor, mTextColor);

        int gravityType = typedArray.getInt(R.styleable.ScrollingHintViewStyle_shvGravity, GRAVITY_LEFT);
        switch (gravityType) {
            case GRAVITY_LEFT:
                mGravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
                break;
            case GRAVITY_CENTER:
                mGravity = Gravity.CENTER;
                break;
            case GRAVITY_RIGHT:
                mGravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
                break;
        }
        typedArray.recycle();
    }

    /**
     * 根据字符串列表，启动翻页公告
     *
     * @param notices 字符串列表
     */
    public void startWithList(List<? extends CharSequence> notices) {
        int inAnimResId = R.anim.anim_bottom_in;
        int outAnimResId = R.anim.anim_top_out;
        startWithList(notices, inAnimResId, outAnimResId);
    }

    /**
     * 根据字符串列表, 和动画效果，启动翻页公告
     * @param notices 字符串列表
     * @param inAnimResId  进入动画
     * @param outAnimResID  离开动画
     */
    public void startWithList(List<? extends CharSequence> notices, @AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        if (notices == null || notices.size() == 0) return;
        setNotices(notices);
        postStart(inAnimResId, outAnimResID);
    }

    private void postStart(final @AnimRes int inAnimResId, final @AnimRes int outAnimResID) {
        post(new Runnable() {
            @Override
            public void run() {
                start(inAnimResId, outAnimResID);
            }
        });
    }

    private void start(final @AnimRes int inAnimResId, final @AnimRes int outAnimResID) {
        removeAllViews();
        clearAnimation();

        mPosition = 0;
        addView(createTextView(mNotices.get(mPosition)));

        if (mNotices.size() > 1) {
            setInAndOutAnimation(inAnimResId, outAnimResID);
            startFlipping();
        }

        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isAnimStart) {
                        animation.cancel();
                    }
                    isAnimStart = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mPosition++;
                    if (mPosition >= mNotices.size()) {
                        mPosition = 0;
                    }
                    View view = createTextView(mNotices.get(mPosition));
                    if (view.getParent() == null) {
                        addView(view);
                    }
                    isAnimStart = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    private TextView createTextView(CharSequence text) {
        TextView textView = (TextView) getChildAt((getDisplayedChild() + 1) % 3);
        if (textView == null) {
            textView = new TextView(getContext());
            textView.setGravity(mGravity);
            textView.setTextColor(mTextColor);
            textView.setTextSize(mTextSize);
            textView.setSingleLine(mSingleLine);
        }
        textView.setText(text);
        textView.setTag(mPosition);
        return textView;
    }

    private void setNotices(List<? extends CharSequence> notices) {
        this.mNotices = notices;
    }

    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private void setInAndOutAnimation(@AnimRes int inAnimResId, @AnimRes int outAnimResID) {
        Animation inAnim = AnimationUtils.loadAnimation(getContext(), inAnimResId);
        setInAnimation(inAnim);

        Animation outAnim = AnimationUtils.loadAnimation(getContext(), outAnimResID);
        setOutAnimation(outAnim);
    }
}
