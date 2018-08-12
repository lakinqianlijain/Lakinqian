package com.qlj.lakinqiandemo.views.pinPasswordView;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.qlj.lakinqiandemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/8/10.
 */

public class GesturePoint extends BasePoint {

	public List<Integer> selectedPoint=new ArrayList<>();
	private Animation mAnimation;

	public GesturePoint(Context context, int leftX, int rightX, int topY, int bottomY, ImageView image, int num) {
		super(context, leftX, rightX, topY, bottomY, image, num);
	}

	@Override
	void setStateWrong() {
		getImage().setImageResource(R.drawable.ic_settings_pattern_point_wrong);
		getImage().setBackgroundResource(R.color.transparent);
	}

	@Override
	void setStateSelected() {
//		getImage().setImageResource(R.drawable.ic_settings_pattern_point_pressed);
		getImage().setBackgroundResource(R.color.transparent);

		mAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_gesture_press);
		getImage().startAnimation(mAnimation);
	}

	@Override
	void setStateNormal() {
		getImage().setImageResource(R.drawable.ic_settings_pattern_point);
		getImage().setBackgroundResource(R.color.transparent);
	}

}
