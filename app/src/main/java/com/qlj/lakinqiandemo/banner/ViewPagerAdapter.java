package com.qlj.lakinqiandemo.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qlj.lakinqiandemo.R;

import java.util.List;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mImageUrls;

    public ViewPagerAdapter(Context context, List<String> urls) {
        this.mContext = context;
        this.mImageUrls = urls;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(mContext, R.layout.banner_item, null);
        final ImageView image = view.findViewById(R.id.iv_banner_image);
        position %= mImageUrls.size();
        if (position < 0) {
            position = mImageUrls.size() + position;
        }
        String url = mImageUrls.get(position);
        Glide.with(mContext).load(url).into(image);
        // 设置监听viewPager的点击事件
        image.setTag(position);
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = (Integer) image.getTag();
                // TODO 处理图片的点击事件 一般是跳转到广告的详情界面
                Toast.makeText(mContext, "position = " + position, Toast.LENGTH_SHORT).show();

            }
        });
        (container).addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}
