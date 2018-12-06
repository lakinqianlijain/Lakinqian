package com.qlj.lakinqiandemo.banner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.banner.BGABanner.BGABanner;
import com.qlj.lakinqiandemo.banner.BGABanner.BGALocalImageSize;
import com.qlj.lakinqiandemo.banner.BannerViewPager;
import com.qlj.lakinqiandemo.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2018/12/6.
 */

public class BannerActivity extends BaseActivity {
    private BannerViewPager mViewPager;
    private List<View> dotList = new ArrayList<View>();
    ;
    private ArrayList<String> imageUrls = new ArrayList<String>();
    private ArrayList<String> desclist = new ArrayList<String>();
    private LinearLayout topPagerLayout;
    private LinearLayout dots_ll;
    private TextView mTvdesc;
    private BGABanner mBanner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initViews();
        initData();
    }

    private void initData() {
        imageUrls
                .add("http://www.sinaimg.cn/dy/slidenews/1_img/2016_42/2841_740089_670674.jpg");
        imageUrls
                .add("http://n.sinaimg.cn/sinanews/20161019/pj5u-fxwvpat5077743.jpg");
        imageUrls
                .add("http://n.sinaimg.cn/photo/20161019/fooN-fxwvpaq1734085.jpg");
        imageUrls.add("http://n.sinaimg.cn/news/20161019/PFu3-fxwvpaq1755605.jpg");
        imageUrls.add("http://n.sinaimg.cn/photo/20161019/muZP-fxwvpar8459655.jpg");
        desclist.add("曼谷红灯区一家酒吧关门打烊");
        desclist.add("新青年】十八线艺人的经纪人:在圈里混却闻不到星味儿");
        desclist.add("如何用乐高拍出电影场景|拍答课堂");
        desclist.add("“青年拍北京”北京国际青年旅游季");
        desclist.add("跨界青春、观念和时尚,两大轻摄导师如何碰撞");
        initDot(imageUrls.size());


        showViewPageView();
    }

    public void showViewPageView() {
        //初始化控件
        mViewPager = new BannerViewPager(this);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        //设置数据源
        mViewPager.setImageUrlLists(imageUrls);
        mViewPager.setDotList(dotList);
        mViewPager.setTitleList(mTvdesc, desclist);
        //设置是否开启自动轮播 （默认不开启）
        mViewPager.setisRoll(false);
        mViewPager.showBanner();
        //添加控件到viewGroup中去
        topPagerLayout.removeAllViews();
        topPagerLayout.addView(mViewPager);

    }

    private void initDot(int size) {
        dots_ll.removeAllViews();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DensityUtil.dip2px(this, 6), DensityUtil.dip2px(this, 6));

            params.setMargins(5, 0, 5, 0);

            View m = new View(this);

            m.setLayoutParams(params);

            if (i == 0) {
                m.setBackgroundResource(R.drawable.dot_focused);
            } else {
                m.setBackgroundResource(R.drawable.dot_normal);
            }

            dotList.add(m);
            dots_ll.addView(m);
        }

    }

    private void initViews() {
        topPagerLayout = findViewById(R.id.ll__view_pager);
        dots_ll = (LinearLayout) findViewById(R.id.dots_ll);
        mTvdesc = (TextView) findViewById(R.id.tv_titledesc);
        mBanner = findViewById(R.id.banner_guide);
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        mBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.guide_image_one,
                R.drawable.guide_image_two);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
