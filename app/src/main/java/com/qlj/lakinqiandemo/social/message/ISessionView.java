package com.qlj.lakinqiandemo.social.message;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

/**
 * Created by lakinqian on 2019/3/7.
 */

public interface ISessionView {
    RecyclerView getContentRecycler();
    EditText getEtContent();
    Context getContext();
}
