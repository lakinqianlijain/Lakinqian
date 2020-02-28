package com.qlj.lakinqiandemo.views.canvas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

public class CanvasActivity extends BaseActivity implements View.OnClickListener {
    DrawView mDrawView;
    ImageView mCanvasBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        initViews();
    }

    private void initViews() {
        FrameLayout root = findViewById(R.id.fl_canvas);
        mDrawView = new DrawView(this);
        root.addView(mDrawView);
        mCanvasBitmap = findViewById(R.id.iv_canvas_to_bitmap);
        findViewById(R.id.tv_canvas1).setOnClickListener(this);
        findViewById(R.id.tv_canvas2).setOnClickListener(this);
        findViewById(R.id.tv_canvas3).setOnClickListener(this);
        findViewById(R.id.tv_canvas4).setOnClickListener(this);
        findViewById(R.id.tv_canvas5).setOnClickListener(this);
        findViewById(R.id.tv_canvas6).setOnClickListener(this);
        findViewById(R.id.tv_canvas7).setOnClickListener(this);
        findViewById(R.id.tv_canvas8).setOnClickListener(this);
        findViewById(R.id.tv_canvas9).setOnClickListener(this);
        findViewById(R.id.tv_canvas10).setOnClickListener(this);
        findViewById(R.id.tv_canvas11).setOnClickListener(this);
        findViewById(R.id.tv_canvas12).setOnClickListener(this);
        findViewById(R.id.tv_canvas13).setOnClickListener(this);
        findViewById(R.id.tv_canvas14).setOnClickListener(this);

        findViewById(R.id.tv_canvas15).setOnClickListener(this);
        findViewById(R.id.tv_canvas16).setOnClickListener(this);
        findViewById(R.id.tv_canvas17).setOnClickListener(this);
        findViewById(R.id.tv_canvas18).setOnClickListener(this);
        findViewById(R.id.tv_canvas19).setOnClickListener(this);
        findViewById(R.id.tv_canvas20).setOnClickListener(this);
        findViewById(R.id.tv_canvas21).setOnClickListener(this);
        findViewById(R.id.tv_canvas22).setOnClickListener(this);
        findViewById(R.id.tv_canvas23).setOnClickListener(this);
        findViewById(R.id.tv_canvas24).setOnClickListener(this);
        findViewById(R.id.tv_canvas25).setOnClickListener(this);
        findViewById(R.id.tv_canvas26).setOnClickListener(this);
        findViewById(R.id.tv_canvas27).setOnClickListener(this);
        findViewById(R.id.tv_canvas28).setOnClickListener(this);
        findViewById(R.id.tv_canvas29).setOnClickListener(this);
        findViewById(R.id.tv_canvas30).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_canvas1:
                mDrawView.initType(1);
                break;
            case R.id.tv_canvas2:
                mDrawView.initType(2);
                break;
            case R.id.tv_canvas3:
                mDrawView.initType(3);
                break;
            case R.id.tv_canvas4:
                mDrawView.initType(4);
                break;
            case R.id.tv_canvas5:
                mDrawView.initType(5);
                break;
            case R.id.tv_canvas6:
                mDrawView.initType(6);
                break;
            case R.id.tv_canvas7:
                mDrawView.initType(7);
                break;
            case R.id.tv_canvas8:
                mDrawView.initType(8);
                break;
            case R.id.tv_canvas9:
                CanvasToBitmap();
                break;
            case R.id.tv_canvas10:
                mDrawView.initType(9);
                break;
            case R.id.tv_canvas11:
                mDrawView.initType(10);
                break;
            case R.id.tv_canvas12:
                mDrawView.initType(11);
                break;
            case R.id.tv_canvas13:
                mDrawView.initType(12);
                break;
            case R.id.tv_canvas14:
                mDrawView.initType(13);
                break;

            case R.id.tv_canvas15:
                mDrawView.initType(13, 1);
                break;
            case R.id.tv_canvas16:
                mDrawView.initType(13, 2);
                break;
            case R.id.tv_canvas17:
                mDrawView.initType(13, 3);
                break;
            case R.id.tv_canvas18:
                mDrawView.initType(13, 4);
                break;
            case R.id.tv_canvas19:
                mDrawView.initType(13, 5);
                break;
            case R.id.tv_canvas20:
                mDrawView.initType(13, 6);
                break;
            case R.id.tv_canvas21:
                mDrawView.initType(13, 7);
                break;
            case R.id.tv_canvas22:
                mDrawView.initType(13, 8);
                break;
            case R.id.tv_canvas23:
                mDrawView.initType(13, 9);
                break;
            case R.id.tv_canvas24:
                mDrawView.initType(13, 10);
                break;
            case R.id.tv_canvas25:
                mDrawView.initType(13, 11);
                break;
            case R.id.tv_canvas26:
                mDrawView.initType(13, 12);
                break;
            case R.id.tv_canvas27:
                mDrawView.initType(13, 13);
                break;
            case R.id.tv_canvas28:
                mDrawView.initType(13, 14);
                break;
            case R.id.tv_canvas29:
                mDrawView.initType(13, 15);
                break;
            case R.id.tv_canvas30:
                mDrawView.initType(13, 16);
                break;





        }
    }

    private void CanvasToBitmap() {
        int w = 300,h = 300;
        Bitmap bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint p = new Paint();
        Path path = new Path();
        path.addCircle(150, 150, 100, Path.Direction.CCW);
        p.setColor(Color.RED);
        canvas.drawPath(path,p);
        mCanvasBitmap.setVisibility(View.VISIBLE);
        mCanvasBitmap.setBackground(new BitmapDrawable(bitmap));
    }
}
