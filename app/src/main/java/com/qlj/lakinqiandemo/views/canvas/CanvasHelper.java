package com.qlj.lakinqiandemo.views.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CanvasHelper {
    private Context mContext;
    private static CanvasHelper mInstance;
    private Paint mNormalPaint;
    private Canvas mNormalCanvas;

    private CanvasHelper (Context context){
        this.mContext = context;
    }

    public static CanvasHelper getInstance(Context context){
        if (mInstance == null){
            synchronized (CanvasHelper.class){
                if (mInstance == null){
                    mInstance = new CanvasHelper(context);
                }
            }
        }
        return mInstance;
    }

    public Paint getNormalPaint(){
        if (mNormalPaint == null){
            initPaint();
        }
        return mNormalPaint;
    }


    private void initPaint() {
        mNormalPaint = new Paint();
//         设置最基本的属性
//         设置画笔颜色
//         可直接引入Color类，如Color.red等
        mNormalPaint.setColor(Color.BLUE);
//         设置画笔模式
        mNormalPaint.setStyle(Paint.Style.FILL);
//         Style有3种类型：
//         类型1：Paint.Style.FILLANDSTROKE（描边+填充）
//         类型2：Paint.Style.FILL（只填充不描边）
//         类型3：Paint.Style.STROKE（只描边不填充）
//         具体差别请看下图：
//         特别注意：前两种就相差一条边
//         若边细是看不出分别的；边粗就相当于加粗
//
//        设置画笔的粗细
//         如设置画笔宽度为10px
        mNormalPaint.setStrokeWidth(10f);

//         不常设置的属性
//         得到画笔的颜色
//        mNormalPaint.getColor()
//         设置Shader
//         即着色器，定义了图形的着色、外观
//         可以绘制出多彩的图形
//         具体请参考文章：http://blog.csdn.net/iispring/article/details/50500106
//        Paint.setShader(Shader shader)
//        设置画笔的a,r,p,g值
//        mNormalPaint.setARGB(int a, int r, int g, int b)
//        设置透明度
//        mNormalPaint.setAlpha(int a)
//        得到画笔的Alpha值
//        mNormalPaint.getAlpha()


//         对字体进行设置（大小、颜色）
//         设置字体大小
//        mNormalPaint.setTextSize(float textSize)
//         文字Style三种模式：
//        mNormalPaint.setStyle(Style style);
//         类型1：Paint.Style.FILLANDSTROKE（描边+填充）
//         类型2：Paint.Style.FILL（只填充不描边）
//         类型3：Paint.Style.STROKE（只描边不填充）
//         设置对齐方式
//        setTextAlign（）
//         LEFT：左对齐
//         CENTER：居中对齐
//         RIGHT：右对齐
//        设置文本的下划线
//        setUnderlineText(boolean underlineText)
//        设置文本的删除线
//        setStrikeThruText(boolean strikeThruText)
//        设置文本粗体
//        setFakeBoldText(boolean fakeBoldText)
//         设置斜体
//        mNormalPaint.setTextSkewX(-0.5f);
//         设置文字阴影
//        mNormalPaint.setShadowLayer(5,5,5, Color.YELLOW);
    }

    public Canvas getNormalCanvas (){
        if (mNormalCanvas == null){
            initCanvas();
        }
        return mNormalCanvas;
    }

    private void initCanvas() {
        mNormalCanvas = new Canvas();
    }


}
