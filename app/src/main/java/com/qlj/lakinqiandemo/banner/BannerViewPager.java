package com.qlj.lakinqiandemo.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.qlj.lakinqiandemo.R;

import java.util.List;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class BannerViewPager extends ViewPager {

    private Context mContext;
    /**
     * 图片集合
     */
    private List<String> mImageUrlLists;
    /**
     * 指示器小点集合
     */
    private List<View> mDotList;
    private TextView mTvdesc;
    private List<String> mDesclist;
    private float downX;
    private float downY;

    /**
     * 是否自动滚动（默认不开启）
     */
    private boolean isRoll = false;
    private boolean flag = false;

    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    //=======================对外提供一下方法======================================================

    /**
     * 设置是否开启轮播
     *
     * @param isRoll （可选择是否调用）
     */
    public void setisRoll(boolean isRoll) {
        this.isRoll = isRoll;
    }

    /**
     * 设置图片url集合（这个必须要调用 传入）
     *
     * @param mImageUrlLists
     */
    public void setImageUrlLists(List<String> mImageUrlLists) {
        this.mImageUrlLists = mImageUrlLists;
    }

    /**
     * 设置点集合 （可选择是否调用）
     *
     * @param mDotList
     */
    public void setDotList(List<View> mDotList) {
        this.mDotList = mDotList;
    }


    /**
     * 设置图片描述文字（可选择是否调用）
     *
     * @param titleLists
     */
    public void setTitleList(TextView tv, List<String> titleLists) {
        this.mTvdesc = tv;
        this.mDesclist = titleLists;
        if (mTvdesc != null && mDesclist != null
                && mDesclist.size() > 0) {
            mTvdesc.setText(mDesclist.get(0));
        }

    }


    /**
     * 显示轮播图
     */
    public void showBanner() {
        // 初始化的操作
        if (!flag) {
            flag = true;
            prepareData();

        }
        if (isRoll) {
            // 发送延时消息 5s
            handler.sendEmptyMessageDelayed(100, 5000);
        }
    }

    //======================================end=============================================================



    /**
     * 使用handler来达到viewpager的自动切换
     */
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BannerViewPager.this.setCurrentItem(BannerViewPager.this
                    .getCurrentItem() + 1);
            showBanner();
        }

    };

    @SuppressWarnings("deprecation")
    private void prepareData() {
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(mContext);
        // 初始ViewPager时,使用反射修改滑动速度
        viewPagerScroller.initViewPagerScroll(BannerViewPager.this);
        BannerViewPager.this.setOnPageChangeListener(new MyOnPageChangeListener());
        ViewPagerAdapter adapter = new ViewPagerAdapter(mContext, mImageUrlLists);
        BannerViewPager.this.setAdapter(adapter);
        // 保证初始化的时候从第一个图片开始
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2
                % mImageUrlLists.size();
        BannerViewPager.this.setCurrentItem(item);

        if (mDotList != null && mDotList.size() > 1) {
            mDotList.get(0).setBackgroundResource(R.drawable.dot_focused);
        }
    }


    /**
     * 当前的postion
     */
    private int currentPosition = 0;
    /**
     * 上一个postion
     */
    private int oldPosition = 0;

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int postion) {
            postion = postion % mImageUrlLists.size();
            currentPosition = postion;
            if (mTvdesc != null && mDesclist != null
                    && mDesclist.size() > 0) {
                mTvdesc.setText(mDesclist.get(postion));
            }
            //设置点的选中状态
            if (mDotList != null) {
                mDotList.get(postion).setBackgroundResource(R.drawable.dot_focused);
                mDotList.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
            }
            oldPosition = postion;

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

    }


    /**
     * 重写事件分发 （用户触摸时停止自动滑动效果）
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //通知父View自己要处理touch事件
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = ev.getX();
                downY = ev.getY();
                handler.removeCallbacksAndMessages(null);
                break;

            case MotionEvent.ACTION_UP:
                showBanner();
                break;
            case MotionEvent.ACTION_MOVE:

                float currentX = ev.getX();
                float currentY = ev.getY();

                if (Math.abs(currentX - downX) > Math.abs(currentY - downY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onDetachedFromWindow() {
        handler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }
}
