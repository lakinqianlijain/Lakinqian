package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

/**
 * Created by lakinqian on 2018/8/10.
 */

/**
 * 手势密码容器类
 *
 */
public class GestureEditContentView extends BaseContentView {

	private final int blockWidthDP=60;//圆圈的直径dp值
	private final int spacingDPHorizontal=40;//圆圈之间的水平距离dp值
	private final int spacingDPVertical=40;//圆圈之间的竖直距离dp值

	public GestureEditContentView(Context context, boolean isVerify, String passWord, BaseDrawline.DrawlineCallBack callBack) {
		super(context, isVerify, passWord, callBack);
	}

	@Override
	public BaseDrawline createDrawlineView() {
		return new GestureDrawlineEdit(context, listDigital, isVerify,passWord, callback);
	}

	@Override
	public void initSize() {
		this.blockWidth = DensityUtil.dip2px(context,blockWidthDP);
		this.spacingHorizontal = DensityUtil.dip2px(context, spacingDPHorizontal);
		this.spacingVertical = DensityUtil.dip2px(context, spacingDPVertical);
	}

	@Override
	public void initBackGround() {
	}

	@Override
	public void addChild() {
		for (int i = 0; i < 9; i++) {
			ImageView image = new ImageView(context);
			image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			image.setImageResource(R.drawable.ic_settings_pattern_point);
			image.setBackgroundResource(R.color.transparent);
			this.addView(image,new LayoutParams(blockWidth, blockWidth));
			// 第几行
			int row = i / 3;
			// 第几列
			int col = i % 3;
			// 定义点的每个属性
			int leftX = col * blockWidth + col * spacingHorizontal;
			int topY = row * blockWidth + row * spacingVertical;
			int rightX = leftX + blockWidth;
			int bottomY = topY + blockWidth;

			//圆圈之间线的位置
			BasePoint p = new GesturePointEdit(context,leftX, rightX, topY, bottomY, image,i+1);
			this.listDigital.add(p);
		}
	}

	@Override
	public void layoutChildren() {
		for (int i = 0; i < getChildCount(); i++) {
			//第几行
			int row = i/3;
			//第几列
			int col = i%3;
			View v = getChildAt(i);
			//用于绘制圆圈的位置，d表示X轴方向的偏移量，如果要上下移动整块手绘区域，则将top和bottom参数增加或减小一个合适的偏移量
			int leftX = col * blockWidth + col * spacingHorizontal;
			int topY = row * blockWidth + row * spacingVertical;
			int rightX = leftX + blockWidth;
			int bottomY = topY + blockWidth;

			v.layout(leftX, topY, rightX, bottomY)  ;
		}
	}

	@Override
	public void setDisableImageView() {

	}

	@Override
	public void setAvailableImageView() {

	}

	@Override
	public void setParentView(ViewGroup parent) {
		super.setParentView(parent,blockWidth * 3 + spacingHorizontal * 2,
				blockWidth * 3 + spacingVertical * 2);
	}
}
