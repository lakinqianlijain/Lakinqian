package com.qlj.lakinqiandemo.social.emoticonLib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2019/3/5.
 */

public class EmoticonTab extends RelativeLayout {
    private ImageView mEmotionTypeIcon;
    private int mIconSrc;

    public EmoticonTab(Context context, int iconSrc) {
        super(context);
        mIconSrc = iconSrc;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.emotion_tab, this);
        mEmotionTypeIcon = findViewById(R.id.iv_emotion_type_icon);
        mEmotionTypeIcon.setImageResource(mIconSrc);
    }
}
