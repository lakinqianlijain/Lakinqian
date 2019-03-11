package com.qlj.lakinqiandemo.social.emoticonLib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2019/3/4.
 */

public class ChatKeyboard extends LinearLayout {

    public static final int LAYOUT_ID = R.layout.layout_chat_keyboard;

    public ChatKeyboard(Context context) {
        this(context, null);
    }

    public ChatKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(LAYOUT_ID, this);
        LayoutParams params = new LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        initViews();
    }

    private void initViews() {

    }
}
