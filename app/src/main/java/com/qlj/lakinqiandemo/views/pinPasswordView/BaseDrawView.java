package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
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
	public boolean isDrawEnable = true; // 是否允许绘制
	public BasePoint currentPoint;
	public BaseDrawView.IPassWordCallBack callBack;
	public StringBuilder passWordSb;
	public String type;
	public String passWord;
	public final int DIGITAL_PASSWORD_LEN=4;
	protected ArrayList<DrawLineInfoBean> drawLineInfoBeanArrayList;
	Canvas canvas = new Canvas();

	public BaseDrawView(Context context, List<BasePoint> list,
						String type, String passWord, BaseDrawView.IPassWordCallBack callBack) {
		super(context);
		this.list = list;
		this.callBack = callBack;
		this.type = type;
		this.passWord = passWord;
		this.passWordSb = new StringBuilder();
		drawLineInfoBeanArrayList = new ArrayList<>();
	}

	/**将位图绘制到画布上*/
	@Override
	protected void onDraw(Canvas canvas) {
		for(DrawLineInfoBean bean:drawLineInfoBeanArrayList){
			canvas.drawLine(bean.startX,bean.startY,bean.endX,bean.endY,bean.mPaint);
		}
		super.onDraw(canvas);
	}

	/**
	 *
	 * @param delayTime
	 * 延迟执行时间
	 */
	public void clearDrawViewState(long delayTime) {
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

	public void addNum(int num) {
	}

	public void clearSelectedState(){
		for(BasePoint point:list){
			point.setUnSelected();
		}
	}

	public void clearDrawViewState(long delayTime, boolean drawErrorPath){}

	public interface IPassWordCallBack {
		void onGestureCodeInput(String inputCode);

		void onGestureLineMove();

		void checkPinSuccess();

		void checkPinFail();

		void setPinSuccess(String password);

		void setPinFailed();

		void changePin(String password);

		void showNum(int num, int pos);
	}

}
