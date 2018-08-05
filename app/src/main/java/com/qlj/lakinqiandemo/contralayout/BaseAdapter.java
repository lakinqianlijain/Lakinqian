package com.qlj.lakinqiandemo.contralayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/4.
 */

public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private static final int ITEM_VIDEO_FOLDER = 1;
    private List<Object> mDataList = new ArrayList<>();

    public BaseAdapter(Context context){
        mContext = context;
    }

    public void setData(int num) {
        mDataList.clear();
        for (int i = 0; i<num ; i++) {
            mDataList.add(i);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        int layoutId = R.layout.video_folder_item;
        itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        return new BaseHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object item = mDataList.get(position);
        holder.itemView.setTag(position);
        if (position == 0){
            RecyclerView.LayoutParams l = (RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
            l.setMargins(0, 0,0,0);
            holder.itemView.setLayoutParams(l);
        } else {
            RecyclerView.LayoutParams l = (RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
            l.setMargins(0, -DensityUtil.dip2px(mContext,20), 0,0);
            holder.itemView.setLayoutParams(l);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataList.get(position);
        return ITEM_VIDEO_FOLDER;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_folder_item:
                Toast.makeText(mContext, "点击了Item"+v.getTag(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class BaseHolder extends RecyclerView.ViewHolder {
        private View folderItem;
        public BaseHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == ITEM_VIDEO_FOLDER) {
                folderItem = itemView.findViewById(R.id.ll_folder_item);
                folderItem.setOnClickListener(BaseAdapter.this);
            }
        }
    }
}
