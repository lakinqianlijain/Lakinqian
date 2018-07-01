package com.qlj.lakinqiandemo.animation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/7/1.
 */

public class LeafLoadingView extends View {
    private Resources mResources;
    private Bitmap mLeafBitmap,mOuterBitmap;
    private Paint mBitmapPaint, mWhitePaint, mOrangePaint;

    public LeafLoadingView(Context context) {
        super(context);
        mResources = getResources();
        initBitmap();
        initPaint();
    }

    private void initPaint() {
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setDither(true);
        mBitmapPaint.setFilterBitmap(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(0xfffde399);

        mOrangePaint = new Paint();
        mOrangePaint.setAntiAlias(true);
        mOrangePaint.setColor(0xffffa800);
    }

    private void initBitmap() {
        mLeafBitmap = ((BitmapDrawable)mResources.getDrawable(R.drawable.leaf)).getBitmap();
        mOuterBitmap = ((BitmapDrawable)mResources.getDrawable(R.drawable.leaf_kuang)).getBitmap();
    }


}
