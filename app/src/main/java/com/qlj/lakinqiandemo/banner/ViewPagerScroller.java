package com.qlj.lakinqiandemo.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class ViewPagerScroller extends Scroller {
    private int mScrollDuration = 1000; // 滑动速度

    /**
     * 设置速度速度
     *
     * @param duration
     */
    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
    }
    public ViewPagerScroller(Context context) {
        super(context);
    }

    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    /**
     * 此构找方法在11以上使用
     */
//  public ViewPagerScroller(Context context, Interpolator interpolator,
//          boolean flywheel) {
//      super(context, interpolator, flywheel);
//      // TODO Auto-generated constructor stub
//  }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollDuration);
    }

    /**
     * 通过反射的方式修改viewpager的滑动
     *
     * @param viewPager
     */
    public void initViewPagerScroll(ViewPager viewPager) {
        try {
            //暴力反射
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);

            mScroller.set(viewPager, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
