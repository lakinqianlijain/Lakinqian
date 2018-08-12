package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qlj.lakinqiandemo.views.pinPasswordView.BaseDotView.ENTER_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.BaseDotView.SET_PASSWORD;
import static com.qlj.lakinqiandemo.views.pinPasswordView.BasePoint.POINT_STATE_NORMAL;
import static com.qlj.lakinqiandemo.views.pinPasswordView.BasePoint.POINT_STATE_SELECTED;
import static com.qlj.lakinqiandemo.views.pinPasswordView.BasePoint.POINT_STATE_WRONG;

/**
 * Created by lakinqian on 2018/8/10.
 */

/**
 * 手势密码路径绘制
 */
public class GestureDrawView extends BaseDrawView {

    private Paint paint;// 声明画笔
    private List<Pair<BasePoint, BasePoint>> lineList;// 记录画过的线
    private Map<String, BasePoint> autoCheckPointMap;// 自动选中的情况点
    private List<Integer> selectedPoint = new ArrayList<>();

    public GestureDrawView(Context context, List<BasePoint> list,
                           String type, String passWord, IPassWordCallBack callBack) {
        super(context, list, type, passWord, callBack);
        initPaint();
        this.lineList = new ArrayList<Pair<BasePoint, BasePoint>>();
        initAutoCheckPointMap();
    }

    private void initPaint() {
        paint = new Paint(Paint.DITHER_FLAG);//设置抗抖动
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 2));
        paint.setColor(getResources().getColor(R.color.edit_line_normal_color));// 设置默认连线颜色
        paint.setAntiAlias(true);//设置抗锯齿
    }

    private void initAutoCheckPointMap() {
        autoCheckPointMap = new HashMap<String, BasePoint>();
        autoCheckPointMap.put("1,3", getBasePointByNum(2));
        autoCheckPointMap.put("1,7", getBasePointByNum(4));
        autoCheckPointMap.put("1,9", getBasePointByNum(5));
        autoCheckPointMap.put("2,8", getBasePointByNum(5));
        autoCheckPointMap.put("3,7", getBasePointByNum(5));
        autoCheckPointMap.put("3,9", getBasePointByNum(6));
        autoCheckPointMap.put("4,6", getBasePointByNum(5));
        autoCheckPointMap.put("7,9", getBasePointByNum(8));
    }

    /**
     * 得到对应的点位
     */
    private BasePoint getBasePointByNum(int num) {
        for (BasePoint point : list) {
            if (point.getNum() == num) {
                return point;
            }
        }
        return null;
    }

    // 触摸事件
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDrawEnable() == false) {
            return false;
        }
        // 设置默认连线颜色
        paint.setColor(getResources().getColor(R.color.edit_line_normal_color));
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mov_x = (int) event.getX();
                mov_y = (int) event.getY();
                /**获取按下的点位*/
                currentPoint = getPointAt(mov_x, mov_y);
                if (currentPoint != null) {
                    /**设置按下的点位的状态，保存密码*/
                    currentPoint.setPointState(POINT_STATE_SELECTED, 0, 0);
                    currentPoint.setSelected();//标记已选中的点
                    selectedPoint.add(currentPoint.getNum());
                    passWordSb.append(currentPoint.getNum());
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("6666", "onTouchEvent: move" );
                //清楚痕迹
                clearScreenAndDrawList(false);
                //移动回调
                callBack.onGestureLineMove();
                //获取移动到的位置的点位
                BasePoint pointAt = getPointAt((int) event.getX(),
                        (int) event.getY());
                if (currentPoint == null && pointAt == null) {
                    return true;
                } else {
                    //由间隔处滑到原点处
                    if (currentPoint == null && pointAt != null) {
                        currentPoint = pointAt;
                        /**设置按下的点位的状态，保存密码*/
                        currentPoint.setPointState(POINT_STATE_SELECTED, 0, 0);
                        currentPoint.setSelected();//标记已选中的点
                        selectedPoint.add(currentPoint.getNum());
                        passWordSb.append(currentPoint.getNum());
                    }
                }

                if (pointAt == null
                        || currentPoint.equals(pointAt)
                        || pointAt.isSelected()) {
                    drawLineInfoBeanArrayList.add(new DrawLineInfoBean(currentPoint.getCenterX(),
                            currentPoint.getCenterY(), event.getX(), event.getY(),
                            paint));
                } else {
                    //最后一点标记为选中状态
                    if (pointAt.equals(list.get(list.size() - 1))) {
                        pointAt.setPointState(POINT_STATE_SELECTED, 0, 0);
                        currentPoint.setSelected();
                    }

                    //处理中间点情况
                    BasePoint betweenPoint = getBetweenCheckPoint(currentPoint,
                            pointAt);
                    if (betweenPoint != null
                            && POINT_STATE_SELECTED != betweenPoint
                            .getPointState()) {

                        // 存在中间点并且没有被选中
                        Pair<BasePoint, BasePoint> pair1 = new Pair<BasePoint, BasePoint>(
                                currentPoint, betweenPoint);
                        lineList.add(pair1);
                        passWordSb.append(betweenPoint.getNum());
                        Pair<BasePoint, BasePoint> pair2 = new Pair<BasePoint, BasePoint>(
                                betweenPoint, pointAt);
                        lineList.add(pair2);
                        passWordSb.append(pointAt.getNum());
                        betweenPoint.setPointState(POINT_STATE_SELECTED, 0, 0);
                        currentPoint.setSelected();//标记已选中的点
                        selectedPoint.add(currentPoint.getNum());
                        currentPoint = pointAt;
                    } else {
                        Pair<BasePoint, BasePoint> pair = new Pair<BasePoint, BasePoint>(currentPoint, pointAt);
                        lineList.add(pair);
                        passWordSb.append(pointAt.getNum());
                        selectedPoint.add(pointAt.getNum());
                        currentPoint = pointAt;
                        currentPoint.setPointState(POINT_STATE_SELECTED, 0, 0);
                        currentPoint.setSelected();//标记已选中的点
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                setDrawEnable(false);
                if (passWordSb.length() < DIGITAL_PASSWORD_LEN) {
                    callBack.onGestureCodeInput(passWordSb.toString());
                    return true;
                }
                switch (type){
                    case SET_PASSWORD:
                        callBack.setPinSuccess(passWordSb.toString());
                        break;
                    case ENTER_PASSWORD:
                        if (passWord.equals(passWordSb.toString())){
                            callBack.checkPinSuccess();
                        } else {
                            callBack.checkPinFail();
                        }
                        break;
                }
                break;

            default:
                break;
        }
        return true;
    }

    /**
     * @param delayTime 延迟执行时间
     */
    @Override
    public void clearDrawViewState(long delayTime, boolean drawErrorPath) {
        drawLineInfoBeanArrayList.clear();
        if (delayTime > 0) {
            // 绘制红色提示路线
            if (drawErrorPath) {
                drawErrorPathTip();
            }
            setDrawEnable(false);
        }
        new Handler().postDelayed(new clearStateRunnable(), delayTime);
        super.clearSelectedState();
    }

    final class clearStateRunnable implements Runnable {
        public void run() {
            // 重置passWordSb
            passWordSb = new StringBuilder();
            // 清空保存点的集合
            lineList.clear();
            // 重新绘制界面
            clearScreenAndDrawList(true);
            for (BasePoint p : list) {
                p.setPointState(POINT_STATE_NORMAL, 0, 0);
                p.setUnSelected();
            }
            invalidate();
            setDrawEnable(true);
        }
    }

    private BasePoint getBetweenCheckPoint(BasePoint pointStart,
                                           BasePoint pointEnd) {
        int startNum = pointStart.getNum();
        int endNum = pointEnd.getNum();
        String key = null;
        if (startNum < endNum) {
            key = startNum + "," + endNum;
        } else {
            key = endNum + "," + startNum;
        }
        return autoCheckPointMap.get(key);
    }

    /**
     * 清掉屏幕上所有的线，然后画出集合里面的线
     */
    private void clearScreenAndDrawList(boolean invalidate) {
        //清空画布内容
        drawLineInfoBeanArrayList.clear();
        for (int i = 0; i < lineList.size(); i++) {
            int r = 0, x, y;
            float d = 0;
            x = lineList.get(i).second.getCenterX() - lineList.get(i).first.getCenterX();
            y = lineList.get(i).second.getCenterY() - lineList.get(i).first.getCenterY();


            if (y == 0 && x > 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() + r,
                        lineList.get(i).first.getCenterY(), lineList.get(i).second.getCenterX() - r,
                        lineList.get(i).second.getCenterY(), paint));// 画线
            } else if (y == 0 && x < 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() - r,
                        lineList.get(i).first.getCenterY(), lineList.get(i).second.getCenterX() + r,
                        lineList.get(i).second.getCenterY(), paint));// 画线
            } else if (x == 0 && y > 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX(),
                        lineList.get(i).first.getCenterY() + r, lineList.get(i).second.getCenterX(),
                        lineList.get(i).second.getCenterY() - r, paint));// 画线

            } else if (x == 0 && y < 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX(),
                        lineList.get(i).first.getCenterY() - r, lineList.get(i).second.getCenterX(),
                        lineList.get(i).second.getCenterY() + r, paint));// 画线
            } else if (x > 0 && y > 0) {

                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() + d,
                        lineList.get(i).first.getCenterY() + d, lineList.get(i).second.getCenterX() - d,
                        lineList.get(i).second.getCenterY() - d, paint));// 画线
            } else if (x > 0 && y < 0) {

                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() + d,
                        lineList.get(i).first.getCenterY() - d, lineList.get(i).second.getCenterX() - d,
                        lineList.get(i).second.getCenterY() + d, paint));// 画线
            } else if (x < 0 && y > 0) {

                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() - d,
                        lineList.get(i).first.getCenterY() + d, lineList.get(i).second.getCenterX() + d,
                        lineList.get(i).second.getCenterY() - d, paint));// 画线
            } else if (x < 0 && y < 0) {

                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() - d,
                        lineList.get(i).first.getCenterY() - d, lineList.get(i).second.getCenterX() + d,
                        lineList.get(i).second.getCenterY() + d, paint));// 画线
            }
        }

        if (invalidate) {
            invalidate();
        }
    }

    private void  drawErrorPathTip() {
        paint.setColor(getResources().getColor(R.color.edit_line_error_color));// 设置默认线路颜色
        if (lineList.size() == 0 && currentPoint != null) {
            currentPoint.setPointState(POINT_STATE_WRONG, 0, 0);
        }
        for (int i = 0; i < lineList.size(); i++) {
            int r = 0, x, y;
            float d = 0;
            x = lineList.get(i).second.getCenterX() - lineList.get(i).first.getCenterX();
            y = lineList.get(i).second.getCenterY() - lineList.get(i).first.getCenterY();


            if (i == lineList.size() - 1) {
                lineList.get(i).second.setPointState(POINT_STATE_WRONG, 0, 0);
                lineList.get(i).first.setPointState(POINT_STATE_WRONG, x, y);
            } else {
                lineList.get(i).first.setPointState(POINT_STATE_WRONG, x, y);
                lineList.get(i).second.setPointState(POINT_STATE_WRONG, x, y);
            }

            if (y == 0 && x > 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() + r,
                        lineList.get(i).first.getCenterY(), lineList.get(i).second.getCenterX() - r,
                        lineList.get(i).second.getCenterY(), paint));// 画线
            } else if (y == 0 && x < 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() - r,
                        lineList.get(i).first.getCenterY(), lineList.get(i).second.getCenterX() + r,
                        lineList.get(i).second.getCenterY(), paint));// 画线
            } else if (x == 0 && y > 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX(),
                        lineList.get(i).first.getCenterY() + r, lineList.get(i).second.getCenterX(),
                        lineList.get(i).second.getCenterY() - r, paint));// 画线

            } else if (x == 0 && y < 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX(),
                        lineList.get(i).first.getCenterY() - r, lineList.get(i).second.getCenterX(),
                        lineList.get(i).second.getCenterY() + r, paint));// 画线
            } else if (x > 0 && y > 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() + d,
                        lineList.get(i).first.getCenterY() + d, lineList.get(i).second.getCenterX() - d,
                        lineList.get(i).second.getCenterY() - d, paint));// 画线
            } else if (x > 0 && y < 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() + d,
                        lineList.get(i).first.getCenterY() - d, lineList.get(i).second.getCenterX() - d,
                        lineList.get(i).second.getCenterY() + d, paint));// 画线
            } else if (x < 0 && y > 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() - d,
                        lineList.get(i).first.getCenterY() + d, lineList.get(i).second.getCenterX() + d,
                        lineList.get(i).second.getCenterY() - d, paint));// 画线
            } else if (x < 0 && y < 0) {
                drawLineInfoBeanArrayList.add(new DrawLineInfoBean(lineList.get(i).first.getCenterX() - d,
                        lineList.get(i).first.getCenterY() - d, lineList.get(i).second.getCenterX() + d,
                        lineList.get(i).second.getCenterY() + d, paint));// 画线
            }
        }
        invalidate();
    }

}
