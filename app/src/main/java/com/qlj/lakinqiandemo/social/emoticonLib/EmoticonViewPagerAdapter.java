package com.qlj.lakinqiandemo.social.emoticonLib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.qlj.lakinqiandemo.JianApplication;

import static com.qlj.lakinqiandemo.social.emoticonLib.EmoticonLayout.EMOTICON_PER_PAGE;

/**
 * Created by lakinqian on 2019/3/5.
 */

public class EmoticonViewPagerAdapter extends PagerAdapter {
    private EditText mMessageEditText;
    private int mEmotionLayoutWidth, mEmotionLayoutHeight;
    private IEmoticonSelectedListener listener;
    int mPageCount = 0;
    int mTabPosition = 0;

    public EmoticonViewPagerAdapter(int emotionLayoutWidth, int emotionLayoutHeight, int tabPosition, IEmoticonSelectedListener listener) {
        mEmotionLayoutWidth = emotionLayoutWidth;
        mEmotionLayoutHeight = emotionLayoutHeight;
        mTabPosition = tabPosition;
        Log.e("6666", "EmoticonViewPagerAdapter: " + mEmotionLayoutWidth + "----" + mEmotionLayoutHeight);

//        if (mTabPosition == 0)
        EmoticonManager.loadEmoticon(mTabPosition);
        mPageCount = (int) Math.ceil(EmoticonManager.getDisplayCount() / (float) EMOTICON_PER_PAGE);
        Log.e("6666", "EmoticonViewPagerAdapter: "+mPageCount );
//        else
//            mPageCount = (int) Math.ceil(StickerManager.getInstance().getStickerCategories().get(mTabPosi - 1).getStickers().size() / (float) STICKER_PER_PAGE);

        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mPageCount == 0 ? 1 : mPageCount;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Context context = container.getContext();
        RelativeLayout rl = new RelativeLayout(context);
        rl.setGravity(Gravity.CENTER);

        GridView gridView = new GridView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        gridView.setLayoutParams(params);
        gridView.setGravity(Gravity.CENTER);

        gridView.setTag(position);//标记自己是第几页
        if (mTabPosition == 0) {
            gridView.setOnItemClickListener(mEmoticonListener);
            gridView.setAdapter(new EmoticonAdapter(context, mEmotionLayoutWidth, mEmotionLayoutHeight, position * EMOTICON_PER_PAGE));
            gridView.setNumColumns(EmoticonLayout.EMOTICON_COLUMNS);
        }

        rl.addView(gridView);
        container.addView(rl);
        return rl;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public AdapterView.OnItemClickListener mEmoticonListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            int index = position + (Integer) parent.getTag() * EmoticonLayout.EMOTICON_PER_PAGE;
            int count = EmoticonManager.getDisplayCount();
            if (position == EmoticonLayout.EMOTICON_PER_PAGE || index >= count) {
                if (listener != null) {
                    listener.onEmotionSelected("/DEL");
                }
                onEmoticonSelected("/DEL");
            } else {
                String text = EmoticonManager.getDisplayText((int) id);
                if (!TextUtils.isEmpty(text)) {
                    if (listener != null) {
                        listener.onEmotionSelected(text);
                    }
                    onEmoticonSelected(text);
                }
            }
        }
    };

    private void onEmoticonSelected(String key) {
        if (mMessageEditText == null)
            return;
        Editable editable = mMessageEditText.getText();
        if (key.equals("/DEL")) {
            mMessageEditText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        } else {
            int start = mMessageEditText.getSelectionStart();
            int end = mMessageEditText.getSelectionEnd();
            start = (start < 0 ? 0 : start);
            end = (start < 0 ? 0 : end);
            editable.replace(start, end, key);
            int editEnd = mMessageEditText.getSelectionEnd();
            MoonUtils.replaceEmoticons(JianApplication.get(), editable, 0, editable.toString().length());
            mMessageEditText.setSelection(editEnd);
        }
    }

    public void attachEditText(EditText messageEditText) {
        mMessageEditText = messageEditText;
    }
}
