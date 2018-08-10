package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/8/10.
 */

/**
 * 密码容器类
 *
 */
public abstract class BaseContentView extends ViewGroup {

	public int blockWidth;
	public int spacingHorizontal,spacingVertical;
	public int backgroudWidth;
	public int backgroudSpaceHorizontal,backgroudSpaceVertical;

	/**
	 * 声明一个集合用来封装坐标集合
	 */
	public List<BasePoint> listBackGround,listDigital;
	public Context context;
	public BaseDrawline drawline;
	public int dx,dy;//内容图片相对于背景图片的偏移量
	public boolean isVerify;
	public String passWord;
	public BaseDrawline.DrawlineCallBack callback;

	/**
	 * ImageView的容器，初始化
	 * @param context
	 * @param isVerify 是否为校验密码
	 * @param passWord 用户传入的密码
	 * @param callBack 密码输入完毕的回调
	 */
	public BaseContentView(Context context, boolean isVerify, String passWord, BaseDrawline.DrawlineCallBack callBack) {
		super(context);
		initData(context,isVerify,passWord,callBack);
		//初始化背景
		initBackGround();
		// 添加内容图标
		addChild();
		// 初始化一个可以画线的view
		drawline = createDrawlineView();
//		setBackgroundColor(context.getResources().getColor(R.color.white));
	}

	private void initData(Context context, boolean isVerify, String passWord, BaseDrawline.DrawlineCallBack callBack) {
		this.context = context;
		this.isVerify=isVerify;
		this.passWord=passWord;
		this.callback=callBack;
		this.listBackGround = new ArrayList<>();
		this.listDigital =new ArrayList<>();
		initSize();
	}

	public void setParentView(ViewGroup parent, int width, int height){
		 //得到屏幕的宽度
		LayoutParams layoutParams = new LayoutParams(width, height);
//		parent.addView(this);
		parent.addView(drawline,layoutParams);
		parent.addView(this,layoutParams);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		layoutChildren();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 保留路径delayTime时间长
	 * @param delayTime
	 */
	public void clearDrawlineState(long delayTime) {
		drawline.clearDrawlineState(delayTime);
	}

	public void clearDrawlineState(long delayTime,boolean drawErrorPath) {
		drawline.clearDrawlineState(delayTime,drawErrorPath);
	}

	public String getPasswordSb(){
		return drawline.getPassWordSb();
	}

	public void setPasswordSb(String str){
		drawline.setPassWordSb(new StringBuilder(str));
	}

	public boolean isDrawEnable() {
		return drawline.isDrawEnable();
	}

	public void setDrawEnable(boolean drawEnable) {
		drawline.setDrawEnable(drawEnable);
	}

	public void updatePassword(String pinPassword) {
		drawline.setPassWord(pinPassword);
	}

	public abstract BaseDrawline createDrawlineView();

	public abstract void initSize();

	public abstract void initBackGround();

	public abstract void addChild();

	public abstract void setParentView(ViewGroup parent);

	public abstract void layoutChildren();

	public abstract void setDisableImageView();

	public abstract void setAvailableImageView();
}
