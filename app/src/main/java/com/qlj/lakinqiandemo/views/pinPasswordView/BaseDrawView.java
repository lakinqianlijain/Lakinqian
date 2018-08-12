package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.view.View;

import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/8/10.
 */

/**
 * 密码路径绘制
 * 
 */
public class BaseDrawView extends View {

	public int mov_x;// 声明起点坐标
	public int mov_y;

	public List<BasePoint> list;

	private boolean isDrawEnable = true; // 是否允许绘制
	/**
	 * 屏幕的宽度和高度
	 */
	private int[] screenDispaly;

	public BasePoint currentPoint;
	public BaseDrawView.DrawlineCallBack callBack;
	public StringBuilder passWordSb;
	public boolean isVerify;
	public String passWord;
	public final int DIGITAL_PASSWORD_LEN=4;
	protected ArrayList<DrawLineInfoBean> drawLineInfoBeanArrayList;
	Canvas canvas = new Canvas();

	public BaseDrawView(Context context, List<BasePoint> list,
						boolean isVerify, String passWord, BaseDrawView.DrawlineCallBack callBack) {
		super(context);
		//屏幕参数
		screenDispaly = DensityUtil.getScreenDispaly(context);

		//点的集合
		this.list = list;

		this.callBack = callBack;
		
		this.isVerify = isVerify;
		this.passWord = passWord;
		//密码集
		this.passWordSb = new StringBuilder();
		drawLineInfoBeanArrayList = new ArrayList<>();
	}

	/**将位图绘制到画布上*/
	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

		for(DrawLineInfoBean bean:drawLineInfoBeanArrayList){
			canvas.drawLine(bean.startX,bean.startY,bean.endX,bean.endY,bean.mPaint);
		}
		//super.onDraw(canvas);
	}

	/**
	 *
	 * @param delayTime
	 * 延迟执行时间
	 */
	public void clearDrawlineState(long delayTime) {
		if (delayTime > 0) {
			isDrawEnable = false;
		}
		new Handler().postDelayed(new clearStateRunnable(), delayTime);
	}

	public void setDrawEnable(boolean drawEnable) {
		isDrawEnable = drawEnable;
	}

	/**
	 */
	final class clearStateRunnable implements Runnable {
		public void run() {
			// 重置passWordSb
			passWordSb = new StringBuilder();
			isDrawEnable = true;
		}
	}

	/**
	 * 
	 * @param x x轴坐标值
	 * @param y y轴坐标值
	 */
	public BasePoint getPointAt(int x, int y) {

		for (BasePoint point : list) {
			// 先判断x
			int leftX = point.getLeftX();
			int rightX = point.getRightX();
			if (!(x >= leftX && x < rightX)) {
				continue;
			}

			int topY = point.getTopY();
			int bottomY = point.getBottomY();
			if (!(y >= topY && y < bottomY)) {
				continue;
			}

			// 如果执行到这，那么说明当前点击的点的位置在point
			return point;
		}

		return null;
	}

	public String getPassWordSb() {
		return passWordSb.toString();
	}

	public void setPassWordSb(StringBuilder passWordSb) {
		this.passWordSb = passWordSb;
	}

	public boolean isDrawEnable() {
		return isDrawEnable;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public interface DrawlineCallBack {

		void onGestureCodeInput(String inputCode);

		void checkedSuccess();

		void checkedFail();

		void onGestureLineMove();

		void onPointDown();

		void showNum(int num, int pos);
	}

	//hook方法，子类可选择实现
	public void addNum(int num) {
	}

	public void clearSelectedState(){
		for(BasePoint point:list){
//			point.setPointState(Constants.POINT_STATE_NORMAL,0,0);
			point.setUnSelected();
		}
	}

	public void clearDrawlineState(long delayTime,boolean drawErrorPath){}

}
