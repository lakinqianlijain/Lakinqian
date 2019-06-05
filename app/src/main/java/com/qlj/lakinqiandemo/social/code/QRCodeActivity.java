package com.qlj.lakinqiandemo.social.code;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.code.common.QRCodeEncoder;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.social.utils.GlideRoundTransform;
import com.qlj.lakinqiandemo.threadpool.ThreadPool;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.concurrent.ExecutionException;

/**
 * Created by lakinqian on 2019/3/13.
 */

public class QRCodeActivity extends BaseActivity {
    private ImageView mQRCode;
    private ImageView mAtavar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soc_qr_code);
        initViews();
    }

    private void initViews() {
        mQRCode = findViewById(R.id.iv_content_code);
        mAtavar = findViewById(R.id.iv_user_avatar);
        initData("hi, my name is lakin");
    }

    private void initData(final String content) {
        Glide.with(this).load("http://img95.699pic.com/element/40028/1996.png_860.png")
                .transform(new GlideRoundTransform(this)).into(mAtavar);
        ThreadPool.getInstance().submit(new ThreadPool.Job<Object>() {
            @Override
            public Object run(ThreadPool.JobContext jc) {
                Bitmap logo = null;
                try {
                    logo = Glide.with(QRCodeActivity.this).load("http://img95.699pic.com/element/40028/1996.png_860.png")
                            .asBitmap().into(DensityUtil.dip2px(QRCodeActivity.this, 20), DensityUtil.dip2px(QRCodeActivity.this, 20))
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                final Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, DensityUtil.dip2px(QRCodeActivity.this, 200), logo);

                ThreadPool.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mQRCode.setImageBitmap(bitmap);
                    }
                });
                return null;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
