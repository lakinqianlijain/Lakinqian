package com.qlj.lakinqiandemo.social.emoticonLib;

public interface IEmoticonSelectedListener {
    void onEmotionSelected(String key);

    void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath);
}
