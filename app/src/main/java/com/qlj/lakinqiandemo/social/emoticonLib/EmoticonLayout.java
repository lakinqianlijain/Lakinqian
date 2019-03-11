package com.qlj.lakinqiandemo.social.emoticonLib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

/**
 * Created by lakinqian on 2019/3/5.
 */

public class EmoticonLayout extends LinearLayout implements View.OnClickListener {
    public static final int EMOTICON_COLUMNS = 5;
    public static final int EMOTICON_ROWS = 3;
    public static final int EMOTICON_PER_PAGE = EMOTICON_COLUMNS * EMOTICON_ROWS - 1;//最后一个是删除键

    public static final int STICKER_COLUMNS = 5;
    public static final int STICKER_ROWS = 3;
    public static final int STICKER_PER_PAGE = STICKER_COLUMNS * STICKER_ROWS;

    private Context mContext;
    private ViewPager mEmotionPager;
    private LinearLayout mPageNumber, mTabContainer;
    private int mTabCount;
    private int mTabPosition = 0;
    private SparseArray<View> mTabViewArray = new SparseArray<>();
    private int mMeasuredWidth, mMeasuredHeight;
    private IEmoticonSelectedListener mEmotionSelectedListener;
    private EditText mMessageEditText;

    public EmoticonLayout(Context context) {
        this(context, null);
    }

    public EmoticonLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
        initTabs();
        initListener();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.emotion_layout, this);
        mEmotionPager = findViewById(R.id.vp_emotion);
        mPageNumber = findViewById(R.id.ll_page_number);
        mTabContainer = findViewById(R.id.ll_tab_container);
    }

    private void initListener() {
        if (mTabContainer != null) {
            mTabCount = mTabContainer.getChildCount();
            for (int position = 0; position < mTabCount; position++) {
                View tab = mTabContainer.getChildAt(position);
                tab.setTag(position);
                tab.setOnClickListener(this);
            }
        }

        mEmotionPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setCurPageCommon(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTabs() {
        EmoticonTab emotionTab = new EmoticonTab(mContext, R.drawable.ic_tab_emoji);
        mTabContainer.addView(emotionTab);
        mTabViewArray.put(0, emotionTab);
        EmoticonTab stickerTab = new EmoticonTab(mContext, R.drawable.ic_tab_emoji);
        mTabContainer.addView(stickerTab);
        mTabViewArray.put(1, stickerTab);

        selectTab(0);
    }

    private void selectTab(int tabPosition) {
        for (int i = 0; i < mTabCount; i++) {
            View tab = mTabViewArray.get(i);
            tab.setBackgroundResource(R.drawable.shape_tab_normal);
        }
        mTabViewArray.get(tabPosition).setBackgroundResource(R.drawable.shape_tab_press);

        fillTabEmotion(tabPosition);
    }

    private void fillTabEmotion(int tabPosition) {
        EmoticonViewPagerAdapter adapter = new EmoticonViewPagerAdapter(mMeasuredWidth, mMeasuredHeight, tabPosition, mEmotionSelectedListener);
        mEmotionPager.setAdapter(adapter);
        mPageNumber.removeAllViews();
        setCurPageCommon(0);
        if (tabPosition == 0) {
            adapter.attachEditText(mMessageEditText);
        }
    }

    private void setCurPageCommon(int position) {
        setCurPage(position, (int) Math.ceil(EmoticonManager.getDisplayCount() / (float) EMOTICON_PER_PAGE));
//        else {
//            StickerCategory category = StickerManager.getInstance().getStickerCategories().get(mTabPosi - 1);
//            setCurPage(position, (int) Math.ceil(category.getStickers().size() / (float) EmotionLayout.STICKER_PER_PAGE));
//        }
    }

    private void setCurPage(int position, int pageCount) {
        int hasCount = mPageNumber.getChildCount();
        int forMax = Math.max(hasCount, pageCount);

        ImageView ivCur = null;
        for (int i = 0; i < forMax; i++) {
            if (pageCount <= hasCount) {
                if (i >= pageCount) {
                    mPageNumber.getChildAt(i).setVisibility(View.GONE);
                    continue;
                } else {
                    ivCur = (ImageView) mPageNumber.getChildAt(i);
                }
            } else {
                if (i < hasCount) {
                    ivCur = (ImageView) mPageNumber.getChildAt(i);
                } else {
                    ivCur = new ImageView(mContext);
                    ivCur.setBackgroundResource(R.drawable.selector_view_pager_indicator);
                    LayoutParams params = new LayoutParams(DensityUtil.dip2px(JianApplication.get(), 8), DensityUtil.dip2px(JianApplication.get(), 8));
                    ivCur.setLayoutParams(params);
                    params.leftMargin = DensityUtil.dip2px(JianApplication.get(), 3);
                    params.rightMargin = DensityUtil.dip2px(JianApplication.get(), 3);
                    mPageNumber.addView(ivCur);
                }
            }

            ivCur.setId(i);
            ivCur.setSelected(i == position);
            ivCur.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasuredWidth = measureWidth(widthMeasureSpec);
        mMeasuredHeight = measureHeight(heightMeasureSpec);
        setMeasuredDimension(mMeasuredWidth, mMeasuredHeight);
        Log.e("6666", "onMeasure: "+mMeasuredWidth+"----"+mMeasuredHeight );
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = DensityUtil.dip2px(JianApplication.get(), 200);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = DensityUtil.dip2px(JianApplication.get(), 200);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }


    @Override
    public void onClick(View v) {
        Log.e("6666", "onClick: "+v.getTag() );
        mTabPosition = (int) v.getTag();
        selectTab(mTabPosition);
    }

    public void attachEditText(EditText messageEditText) {
        mMessageEditText = messageEditText;
    }
}
