package com.qlj.lakinqiandemo.share;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.qlj.lakinqiandemo.R;

/**
 * Created by lakinqian on 2019/2/20.
 */

public class ShareBottomDialog extends Dialog implements View.OnClickListener {
    public static final int FACEBOOK = 1;
    public static final int WHATAPP = 2;

    private OnItemClickListener mListener;
    private Context mContext;

    public ShareBottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private void init() {
        setContentView(R.layout.dialog_share);
        setCanceledOnTouchOutside(true);
        initViews();
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        layoutParams.width = wm.getDefaultDisplay().getWidth();
        getWindow().setAttributes(layoutParams);
//        getWindow().setWindowAnimations(R.style.menu_popup_window);
    }

    private void initViews() {
        findViewById(R.id.rl_root).setOnClickListener(this);
        findViewById(R.id.tv_facebook).setOnClickListener(this);
        findViewById(R.id.tv_Whatapp).setOnClickListener(this);
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.ll_share_panel).setOnClickListener(null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_root:
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_facebook:
                if (mListener != null) {
                    mListener.onClick(this, FACEBOOK);
                    dismiss();
                }
                break;

            case R.id.tv_Whatapp:
                if (mListener != null) {
                    mListener.onClick(this, WHATAPP);
                    dismiss();
                }
                break;
        }
    }

    public interface OnItemClickListener {
        void onClick(Dialog dialog, int position);
    }
}
