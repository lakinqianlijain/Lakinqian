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

    String[] urls = {
            "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg",
            "http://desk-fd.zol-img.com.cn/t_s960x600c5/g5/M00/00/09/ChMkJ1cptF2IOTKzACKPLofiF7oAARA4gKz1JIAIo9G447.jpg",
            "http://www.2345.com/hot/pic2012/img/zrfg/1440x900_03.jpg",
            "http://pic4.zhimg.com/v2-2266034dfe42ad77edd75394a533124d_1200x500.jpg",
            "http://img.boqiicdn.com/Data/BK/P/imagick74411547023178.jpg",
            "http://x0.ifengimg.com/res/2019/FE60DCB9D89E4D81BCD84F826AEA0FB1B90809B4_size36_w500_h499.jpeg",
            "http://public.potaufeu.asahi.com/bba0-p/picture/11471086/6d9ad1e1a9fd0faa3205dc7dc1c6fd22_640px.jpg",
            "http://img10.360buyimg.com/cms/jfs/t9601/209/212839184/86173/80feaa48/59c8bd1eN21cad56e.jpg",
            "http://n.sinaimg.cn/sinacn20190430s/110/w580h330/20190430/Lg3F-hwfpcxn1631521.jpg",
            "http://pic.iresearch.cn/news/201904/9278b092-17b5-4725-a407-799a13afeb0c.jpeg"

    };


    private ImageView mImageView;
    private Bitmap mBitmap;

    List<Bitmap> picasso = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);
        initViews();

        CommUtil commUtil = CommUtil.getInstance(this);
//        解决办法：使用Application的上下文
//        CommonUtil生命周期跟MainActivity不一致，而是跟Application进程同生同死。
//        CommUtil commUtil = CommUtil.getInstance(getApplicationContext());

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
//        mBitmap.recycle();         // 释放
//        System.gc();
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
                            for (int i = 0; i < 50; i++) {
                                glide.add(mBitmap);
                                Log.e("6666", "tv_glide: ");
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
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
//                            Bitmap bitmap = Glide.with(ImageLoadActivity.this).load(R.drawable.timg).asBitmap().centerCrop().into(200, 200).get();
                            for (int i = 0; i < 10; i++) {
                                Log.e("6666", "tv_picasso: ");
                                picasso.add(Glide.with(ImageLoadActivity.this).load(urls[i]).asBitmap().centerCrop().into(1980, 1020).get());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread1.start();
                Picasso.get().load(url).placeholder(R.drawable.bg_gradient_grey).into(mImageView);

                break;
        }
    }
}
