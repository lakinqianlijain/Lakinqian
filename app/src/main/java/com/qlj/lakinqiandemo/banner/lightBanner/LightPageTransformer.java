package com.qlj.lakinqiandemo.banner.lightBanner;

import android.support.v4.view.ViewPager;
import android.view.View;

public class LightPageTransformer implements ViewPager.PageTransformer {

    public void transformPage(View view, float position) {
        if (position < -1.0f) {
            // [-Infinity,-1)
            // This page is way off-screen to the left.
            handleInvisiblePage(view, position);
        } else if (position <= 0.0f) {
            // [-1,0]
            // Use the default slide transition when moving to the left page
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            // (0,1]
            handleRightPage(view, position);
        } else {
            // (1,+Infinity]
            // This page is way off-screen to the right.
            handleInvisiblePage(view, position);
        }
    }

    public void handleInvisiblePage(View view, float position) {
    }

    public void handleLeftPage(View view, float position) {
    }

    public void handleRightPage(View view, float position) {
    }
}