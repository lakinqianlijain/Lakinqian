package com.qlj.lakinqiandemo.views.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.R;

public class DrawView extends View {

    private Paint mPaint;
    private int mType = -1;
    private Canvas mCanvas;
    private Path mPath = new Path();
    private int mXfermode = -1;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public void initType(int type) {
        this.mType = type;
        this.draw(mCanvas);
    }

    public void initType(int type, int Xfermode) {
        this.mType = type;
        this.mXfermode = Xfermode;
        this.draw(mCanvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        if (mType != -1) realDrawView();
    }

    private void realDrawView() {
        mCanvas.save();
        switch (mType) {
            case 1:
                drawRect();
                break;
            case 2:
                drawPath1OfSetLastPointAndMoveTo();
                break;
            case 3:
                drawPath2();
                break;
            case 4:
                drawPath3();
                break;
            case 5:
                drawPath4();
                break;
            case 6:
                drawPath5();
                break;
            case 7:
                drawPath6();
                break;
            case 8:
                drawPath7();
                break;
            case 9:
                drawArc();
                break;
            case 10:
                drawText();
                break;
            case 11:
                drawPicture();
                break;
            case 12:
                drawBitmap();
                break;
            case 13:
                drawPorterDuffXfermode(mXfermode);
                break;

        }
        mCanvas.restore();
    }

    private void drawPorterDuffXfermode(int xfermode) {
        mPaint.setStyle(Paint.Style.FILL);

        //设置背景色
        mCanvas.drawARGB(255, 139, 197, 186);

        int canvasWidth = mCanvas.getWidth();
        int canvasHeight = mCanvas.getHeight();
        int layerId = mCanvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        int r = canvasWidth / 3;
        //正常绘制黄色的圆形
        mPaint.setColor(0xFFFFCC44);
        mCanvas.drawCircle(r, r, r, mPaint);
        //设置Xfermode
        setXfermode(xfermode);
        mPaint.setColor(0xFF66AAFF);
        mCanvas.drawRect(r, r, r * 2.7f, r * 2.7f, mPaint);
        //最后将画笔去除Xfermode
        mPaint.setXfermode(null);
        mCanvas.restoreToCount(layerId);
    }

    private void setXfermode(int xfermode) {
        switch (xfermode) {
            case 1:
                //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                break;
            case 2:
                //使用SRC作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                break;
            case 3:
                //使用DST作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
                break;
            case 4:
                //使用SRC_OVER作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                break;
            case 5:
                //使用DST_OVER作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
                break;
            case 6:
                //使用SRC_IN作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                break;
            case 7:
                //使用DST_IN作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                break;
            case 8:
                //使用SRC_OUT作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                break;
            case 9:
                //使用DST_OUT作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                break;
            case 10:
                //使用SRC_ATOP作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                break;
            case 11:
                //使用DST_ATOP作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                break;
            case 12:
                //使用XOR作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
                break;
            case 13:
                //使用DARKEN作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
                break;
            case 14:
                //使用LIGHTEN作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
                break;
            case 15:
                //使用MULTIPLY作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
                break;
            case 16:
                //使用SCREEN作为PorterDuffXfermode绘制蓝色的矩形
                mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
                break;
        }

    }

    private void drawBitmap() {
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.timg);
        // 方法1
//        mCanvas.drawBitmap(bitmap, new Matrix(), new Paint());
        // 方法2
//        mCanvas.drawBitmap(bitmap, 300, 400, new Paint());
        // 方法3
        // 参数（src，dst） = 两个矩形区域
// Rect src：指定需要绘制图片的区域（即要绘制图片的哪一部分）
// Rect dst 或RectF dst：指定图片在屏幕上显示(绘制)的区域
// 下面我将用实例来说明

        // 仅绘制图片的二分之一
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        // 指定图片在屏幕上显示的区域
        Rect dst = new Rect(100, 100, 850, 850);
        mCanvas.drawBitmap(bitmap, src, dst, null);
    }

    private void drawPicture() {
        Picture picture = new Picture();
        // 注：要创建Canvas对象来接收beginRecording()返回的Canvas对象
        Canvas recordingCanvas = picture.beginRecording(500, 500);
        // 位移
        // 将坐标系的原点移动到(450,650)
        recordingCanvas.translate(450, 650);

        // 记得先创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        // 绘制一个圆
        // 圆心为（0，0），半径为100
        recordingCanvas.drawCircle(0, 0, 100, paint);
        // 结束绘制
        picture.endRecording();
        // 将存储在Picture的绘制内容绘制出来
        // 方法一：Picture提供的draw（）
//        picture.draw(mCanvas);
        // 方法二：Canvas提供的drawPicture（）
//        mCanvas.drawPicture(picture);
        // 方法三：使用PictureDrawable的draw方法绘制
        PictureDrawable drawable = new PictureDrawable(picture);
        drawable.draw(mCanvas);

    }

    private void drawText() {
        mPaint.setStyle(Paint.Style.FILL);
//         参数text：要绘制的文本
// 参数x，y：指定文本开始的位置（坐标）

// 参数paint：设置的画笔属性
//        public void drawText (String text, float x, float y, Paint paint)

// 实例
        mCanvas.drawText("abcdefg", 300, 300, mPaint);

// 仅绘制文本的一部分
// 参数start，end：指定绘制文本的位置
// 位置以下标标识，由0开始
//        public void drawText (String text, int start, int end, float x, float y, Paint paint)
//        public void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)

// 对于字符数组char[]
// 截取文本使用起始位置(index)和长度(count)
//        public void drawText (char[] text, int index, int count, float x, float y, Paint paint)

// 实例：绘制从位置1-3的文本
        mCanvas.drawText("abcdefg", 1, 4, 300, 400, mPaint);
//
//         字符数组情况
//         字符数组(要绘制的内容)
        char[] chars = "abcdefg".toCharArray();
//
//        // 参数为 (字符数组 起始坐标 截取长度 基线x 基线y 画笔)
        mCanvas.drawText(chars, 1, 3, 200, 500, mPaint);
        // 效果同上


        // 绘制文本到指定位置
//         参数text：绘制的文本
// 参数pos：数组类型，存放每个字符的位置（坐标）
// 注意：必须指定所有字符位置
//        public void drawPosText (String text, float[] pos, Paint paint)

//         对于字符数组char[],可以截取部分文本进行绘制
// 截取文本使用起始位置(index)和长度(count)
//        public void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)

// 特别注意：
// 1. 在字符数量较多时，使用会导致卡顿
// 2. 不支持emoji等特殊字符，不支持字形组合与分解

        // 实例
        mCanvas.drawPosText("abcde", new float[]{
                100, 100,    // 第一个字符位置
                200, 200,    // 第二个字符位置
                300, 300,    // ...
                400, 400,
                500, 500
        }, mPaint);


        // 数组情况（绘制部分文本）
        char[] chars2 = "abcdefg".toCharArray();

        mCanvas.drawPosText(chars2, 1, 3, new float[]{
                300, 300,    // 指定的第一个字符位置
                400, 400,    // 指定的第二个字符位置
                500, 500,    // 指定的第三个字符位置

        }, mPaint);


        // 在路径(540,750,640,450,840,600)写上"在Path上写的字:Carson_Ho"字样
        // 1.创建路径对象
        Path path = new Path();
        // 2. 设置路径轨迹
        path.cubicTo(840, 1050, 940, 750, 1140, 900);
        // 3. 画路径

        mPaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawPath(path, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        // 4. 画出在路径上的字
        mCanvas.drawTextOnPath("在Path上写的字:Carson_Ho", path, 500, 100, mPaint);
    }


    private void drawArc() {
        mPaint.setStyle(Paint.Style.FILL);
// 以下示例：绘制两个起始角度为0度、扫过90度的圆弧
// 两者的唯一区别就是是否使用了中心点

        // 绘制圆弧1(无使用中心)
        RectF rectF = new RectF(100, 100, 800, 400);
        // 绘制背景矩形
        mCanvas.drawRect(rectF, mPaint);
        // 绘制圆弧
        mPaint.setColor(Color.RED);
        mCanvas.drawArc(rectF, 0, 90, false, mPaint);

        mPaint.setColor(Color.BLUE);
        // 绘制圆弧2(使用中心)
        RectF rectF2 = new RectF(100, 600, 800, 900);
        // 绘制背景矩形
        mCanvas.drawRect(rectF2, mPaint);
        mPaint.setColor(Color.RED);
        // 绘制圆弧
        mCanvas.drawArc(rectF2, 0, 90, true, mPaint);
    }

    private void drawPath7() {
        // 为了方便观察,平移坐标系
        mCanvas.translate(550, 550);
        mPaint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        Path path2 = new Path();

        // 画两个圆
        // 圆1:圆心 = (0,0),半径 = 100
        // 圆2:圆心 = (50,0),半径 = 100
        path1.addCircle(0, 0, 100, Path.Direction.CW);
        path2.addCircle(50, 0, 100, Path.Direction.CW);

        // 取两个路径的异或集
        path1.op(path2, Path.Op.XOR);
        // 画出路径
        mCanvas.drawPath(path1, mPaint);
    }

    private void drawPath6() {
// 判断path中是否包含内容
//        public boolean isEmpty ()
// 例子：
        Path path = new Path();
        Log.e("6666", "path is empty: " + path.isEmpty());  //返回true

        path.lineTo(100, 100); // 返回true
        Log.e("6666", "path is empty: " + path.isEmpty());  //返回false


// 判断path是否是一个矩形
// 如果是一个矩形的话，会将矩形的信息存放进参数rect中。
//        public boolean isRect (RectF rect)

// 实例
        path.reset();
        path.lineTo(0, 400);
        path.lineTo(400, 400);
        path.lineTo(400, 0);
        path.lineTo(0, 0);

        RectF rect = new RectF();
        boolean b = path.isRect(rect);  // b返回ture,
        Log.e("6666", "path is rect: " + b);  //返回false

        // rect存放矩形参数，具体如下：
        // rect.left = 0
        // rect.top = 0
        // rect.right = 400
        // rect.bottom = 400

// 将新的路径替代现有路径
//        public void set (Path src)

//         实例
//         设置一矩形路径
        Path path2 = new Path();
        path2.addRect(-200, -200, 200, 200, Path.Direction.CW);

        // 设置一圆形路径
        Path src = new Path();
        src.addCircle(0, 0, 100, Path.Direction.CW);

        // 将圆形路径代替矩形路径
        path2.set(src);

        // 绘制图形
        mCanvas.drawPath(path2, mPaint);


// 平移路径
// 与Canvas.translate （）平移画布类似


// 方法1
// 参数x,y：平移位置
//        public void offset (float dx, float dy)

// 方法2
// 参数dst：存储平移后的路径状态，但不影响当前path
// 可通过dst参数绘制存储的路径
//        public void offset (float dx, float dy, Path dst)


        // 为了方便观察,平移坐标系
        mCanvas.translate(350, 500);

        // path中添加一个圆形(圆心在坐标原点)
        Path path3 = new Path();
        path3.addCircle(0, 0, 100, Path.Direction.CW);

        // 平移路径并存储平移后的状态
        Path dst = new Path();
        path3.offset(400, 0, dst);                     // 平移

        mCanvas.drawPath(path3, mPaint);               // 绘制path


        // 通过dst绘制平移后的图形(红色)
        mPaint.setColor(Color.RED);
        mCanvas.drawPath(dst, mPaint);

    }

    private void drawPath5() {
//         方法1
//        public void addPath (Path src)
//
//         方法2
//         先将src进行（x,y）位移之后再添加到当前path
//        public void addPath (Path src, float dx, float dy)
//
//         方法3
//         先将src进行Matrix变换再添加到当前path
//        public void addPath (Path src, Matrix matrix)

        // 实例：合并矩形路径和圆形路径

        // 为了方便观察,平移坐标系
        mCanvas.translate(350, 500);
        // 创建路径的对象
        Path pathRect = new Path();
        Path pathCircle = new Path();
        // 画一个矩形路径
        pathRect.addRect(-200, -200, 200, 200, Path.Direction.CW);
        // 画一个圆形路径
        pathCircle.addCircle(0, 0, 100, Path.Direction.CW);

        // 将圆形路径移动(0,200),再添加到矩形路径里
        pathRect.addPath(pathCircle, 0, 200);

        // 绘制合并后的路径
        mCanvas.drawPath(pathRect, mPaint);
    }

    private void drawPath4() {

// 将一个圆弧路径添加到一条直线路径里

        // 为了方便观察,平移坐标系
        mCanvas.translate(350, 500);

        // 先将原点(0,0)连接点(100,100)
        mPath.lineTo(50, 200);

// 添加圆弧路径(2分之1圆弧)

        // 不连接最后一个点与圆弧起点
        mPath.addArc(new RectF(200, 200, 300, 300), 0, 180);
        // path.arcTo(oval,0,270,true);             // 与上面一句作用等价

        // 连接之前路径的结束点与圆弧起点
        mPath.arcTo(new RectF(400, 400, 500, 500), 0, 180);
        // path.arcTo(oval,0,270,false);             // 与上面一句作用等价

        // 画出路径
        mCanvas.drawPath(mPath, mPaint);

    }

    private void drawPath3() {
        // 轨迹1
        // 将Canvas坐标系移到屏幕正中
        mCanvas.translate(400, 500);

        // 起点是(0,0)，连接点(-100,0)
        mPath.lineTo(-100, 0);
        // 连接点(-100,200)
        mPath.lineTo(-100, 200);
        // 连接点(200,200)
        mPath.lineTo(200, 200);
        // 闭合路径，即连接当前点和起点
        // 即连接(200,200)与起点是(0,0)
        mPath.close();

        // 画出路径
        mCanvas.drawPath(mPath, mPaint);
        // 具体请看下图


// 轨迹2
        // 将Canvas坐标系移到屏幕正中
        mPath.reset();
        mCanvas.translate(400, 500);

        // 起点是(0,0)，连接点(-100,0)
        mPath.lineTo(-100, 0);
        // 画圆：圆心=(0,0)，半径=100px
        // 此时路径起点改变 = (0,100)（记为起点2）
        // 起点改变原则：新画图形在x轴正方向的最后一个坐标
        // 后面路径的变化以这个点继续下去
        mPath.addCircle(0, 0, 100, Path.Direction.CCW);

        // 起点为：(0,100)，连接 (-100,200)
        mPath.lineTo(-100, 200);
        // 连接 (200,200)
        mPath.lineTo(200, 200);

        // 闭合路径，即连接当前点和起点（注：闭合的是起点2）
        // 即连接(200,200)与起点2(0,100)
        mPath.close();

        // 画出路径
        mCanvas.drawPath(mPath, mPaint);

        // // 具体请看下图

    }

    private void drawPath2() {
        mPaint.reset();

        mPaint.setColor(Color.RED);

//      添加圆弧
//      方法1
//      startAngle：确定角度的起始位置
//      sweepAngle ： 确定扫过的角度
        RectF rectF = new RectF(50, 50, 150, 150);
        mPath.addArc(rectF, 30, 180);

        // 方法2
        // 与上面方法唯一不同的是：如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
//        RectF rectF3 = new RectF(210, 210, 300, 300);
//        mPath.arcTo (rectF3, 30, 180);

        // 方法3
        // 参数forceMoveTo：是否将之前路径的结束点设置为圆弧起点
        // true：在新的起点画圆弧，不连接最后一个点与圆弧起点，即与之前路径没有交集（同addArc（））
        // false：在新的起点画圆弧，但会连接之前路径的结束点与圆弧起点，即与之前路径有交集（同arcTo（3参数））

        RectF rectF4 = new RectF(310, 310, 400, 400);
        mPath.arcTo(rectF4, 30, 180, true);
// 下面会详细说明


        // 加入圆形路径
        // 起点：x轴正方向的0度
        // 其中参数dir：指定绘制时是顺时针还是逆时针:CW为顺时针，  CCW为逆时针
        // 路径起点变为圆在X轴正方向最大的点
        mPath.addCircle(420, 620, 50, Path.Direction.CCW);

        // 加入椭圆形路径
        // 其中，参数oval作为椭圆的外切矩形区域
        RectF rectF2 = new RectF(530, 530, 700, 800);
        mPath.addOval(rectF2, Path.Direction.CCW);

        // 加入矩形路径
        // 路径起点变为矩形的左上角顶点
        RectF rectF5 = new RectF(800, 600, 900, 700);
        mPath.addRect(rectF5, Path.Direction.CCW);

        //加入圆角矩形路径

        RectF rectF6 = new RectF(800, 800, 900, 900);
        mPath.addRoundRect(rectF6, 20, 20, Path.Direction.CCW);

//  注：添加图形路径后会改变路径的起点
        // 画出路径
        mCanvas.drawPath(mPath, mPaint);
    }

    private void drawPath1OfSetLastPointAndMoveTo() {
        mPaint.setStyle(Paint.Style.STROKE);

//        // 使用moveTo（）
//        // 起点默认是(0,0)
//        //连接点(400,500)
//        mPath.lineTo(400, 500);
//
//        // 将当前点移动到(300, 300)
//        mPath.moveTo(300, 300) ;
//
//        //连接点(900, 800)
//        mPath.lineTo(900, 800);
//
//        //连接点(200,700)
//        mPath.lineTo(200, 700);
//
//
//        // 闭合路径，即连接当前点和起点
//        // 即连接(200,700)与起点2(300, 300)
//        // 注:此时起点已经进行变换
//        mPath.close();
//
//        // 画出路径
//        mCanvas.drawPath(mPath, mPaint);


// 使用setLastPoint（）
// 起点默认是(0,0)
        //连接点(400,500)
        mPath.lineTo(400, 500);

        // 将当前点移动到(300, 300)
        // 会影响之前的操作
        // 但不将此设置为新起点
        mPath.setLastPoint(300, 300);

        //连接点(900,800)
        mPath.lineTo(900, 800);

        //连接点(200,700)
        mPath.lineTo(200, 700);

        // 闭合路径，即连接当前点和起点
        // 即连接(200,700)与起点(0，0)
        // 注:起点一直没变化
        mPath.close();

        // 画出路径
        mCanvas.drawPath(mPath, mPaint);
    }

    private void drawRect() {
// 画一个矩形(蓝色)
        mPaint.setColor(JianApplication.get().getResources().getColor(R.color.color_blue));
        mCanvas.drawRect(100, 100, 150, 150, mPaint);
// 将画布的原点移动到(400,500)
        mCanvas.translate(400, 500);
// 再画一个矩形(红色)
        mPaint.setColor(JianApplication.get().getResources().getColor(R.color.color_red));
        mCanvas.drawRect(100, 100, 150, 150, mPaint);
    }

    private void initPaint() {
        mPaint = new Paint();
//         设置最基本的属性
//         设置画笔颜色
//         可直接引入Color类，如Color.red等
        mPaint.setColor(Color.BLUE);
//         设置画笔模式
        mPaint.setStyle(Paint.Style.STROKE);
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
        mPaint.setStrokeWidth(10f);

//         不常设置的属性
//         得到画笔的颜色
//        mPaint.getColor()
//         设置Shader
//         即着色器，定义了图形的着色、外观
//         可以绘制出多彩的图形
//         具体请参考文章：http://blog.csdn.net/iispring/article/details/50500106
//        Paint.setShader(Shader shader)
//        设置画笔的a,r,p,g值
//        mPaint.setARGB(int a, int r, int g, int b)
//        设置透明度
//        mPaint.setAlpha(int a)
//        得到画笔的Alpha值
//        mPaint.getAlpha()


//         对字体进行设置（大小、颜色）
//         设置字体大小
        mPaint.setTextSize(40);
//         文字Style三种模式：
//        mPaint.setStyle(Style style);
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
//        mPaint.setTextSkewX(-0.5f);
//         设置文字阴影
//        mPaint.setShadowLayer(5,5,5, Color.YELLOW);
    }
}
