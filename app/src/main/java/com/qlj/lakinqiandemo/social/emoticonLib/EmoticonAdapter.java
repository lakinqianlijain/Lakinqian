package com.qlj.lakinqiandemo.social.emoticonLib;


import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.JianApplication;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

public class EmoticonAdapter extends BaseAdapter {

    private Context mContext;
    private int mStartIndex;
    private int mEmotionLayoutWidth;
    private int mEmotionLayoutHeight;
    private final float mPerWidth;
    private final float mPerHeight;
    private final float mIvSize;

    public EmoticonAdapter(Context context, int emotionLayoutWidth, int emotionLayoutHeight, int startIndex) {
        Log.e("6666", "EmoticonAdapter: "+emotionLayoutWidth+"----"+ emotionLayoutHeight);
        mContext = context;
        mStartIndex = startIndex;
        mEmotionLayoutWidth = emotionLayoutWidth;
        mEmotionLayoutHeight = emotionLayoutHeight - DensityUtil.dip2px(JianApplication.get(), 35 + 26 + 50);//减去底部的tab高度、小圆点的高度才是viewpager的高度，再减少30dp是让表情整体的顶部和底部有个外间距

        mPerWidth = mEmotionLayoutWidth * 1f / EmoticonLayout.EMOTICON_COLUMNS;
        mPerHeight = mEmotionLayoutHeight * 1f / EmoticonLayout.EMOTICON_ROWS;
        float ivWidth = mPerWidth * .6f;
        float ivHeight = mPerHeight * .6f;
        mIvSize = Math.min(ivWidth, ivHeight);
    }

    @Override
    public int getCount() {
        int count = EmoticonManager.getDisplayCount() - mStartIndex + 1;
        count = Math.min(count, EmoticonLayout.EMOTICON_PER_PAGE + 1);
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mStartIndex + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout rl = new RelativeLayout(mContext);
        rl.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, (int) mPerHeight));
//        rl.setBackgroundColor(Color.RED);

        ImageView emojiThumb = new ImageView(mContext);
        int count = EmoticonManager.getDisplayCount();
        int index = mStartIndex + position;
        if (position == EmoticonLayout.EMOTICON_PER_PAGE || index == count) {
            emojiThumb.setBackgroundResource(R.drawable.ic_emoji_del);
        } else if (index < count) {
            Log.e("6666", "getView: " );
            emojiThumb.setBackground(EmoticonManager.getDisplayDrawable(mContext, index));
        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.width = (int) mIvSize;
        layoutParams.height = (int) mIvSize;
        Log.e("6666", "getView: " + ((int) mIvSize));
        emojiThumb.setLayoutParams(layoutParams);

        rl.setGravity(Gravity.CENTER);
        rl.addView(emojiThumb);

        return rl;
    }
}
