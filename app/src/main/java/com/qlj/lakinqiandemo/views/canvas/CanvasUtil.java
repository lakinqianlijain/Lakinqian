package com.qlj.lakinqiandemo.views.canvas;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.R;

public class CanvasUtil {
    public static void drawRect(Paint paint, Canvas canvas){
        Log.e("6666", "drawRect: " );
// 画一个矩形(蓝色)
        paint.setColor(JianApplication.get().getResources().getColor(R.color.color_blue));
        canvas.drawRect(100, 100, 150, 150, paint);
// 将画布的原点移动到(400,500)
        canvas.translate(400,500);
// 再画一个矩形(红色)
        paint.setColor(JianApplication.get().getResources().getColor(R.color.color_red));
        canvas.drawRect(100, 100, 150, 150, paint);
    }
}
