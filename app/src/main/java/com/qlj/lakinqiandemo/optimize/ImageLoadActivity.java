package com.qlj.lakinqiandemo.optimize;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Administrator on 2018/11/25.
 */

public class ImageLoadActivity extends BaseActivity implements View.OnClickListener {
    String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";

    private ImageView mImageView;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.tv_glide).setOnClickListener(this);
        mImageView = findViewById(R.id.iv_image);
        findViewById(R.id.tv_picasso).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mBitmap.recycle();         // 释放
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_glide:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<Bitmap> glide = new ArrayList<>();
                            mBitmap = Glide.with(ImageLoadActivity.this).load(url).asBitmap().centerCrop().into(200, 200).get();
//                            Drawable drawable = (ImageLoadActivity.this).getResources().getDrawable(R.drawable.timg);
//                            List<Drawable> glide = new ArrayList<>();
                            for (int i = 0; i < 500; i++) {
                                glide.add(mBitmap);
                                Log.e("6666", "onClick: " );
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
//                Glide.with(this).load(url).placeholder(R.drawable.bg_gradient_grey)
//                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(mImageView);
                break;
            case R.id.tv_picasso:
                List<Bitmap> picasso = new ArrayList<>();
                try {
                    Bitmap bitmap = Glide.with(this).load(url).asBitmap().centerCrop().into(200, 200).get();
                    for (int i = 0; i < 10; i++) {
                        picasso.add(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Picasso.get().load(url).placeholder(R.drawable.bg_gradient_grey).into(mImageView);
                break;
        }
    }
}
