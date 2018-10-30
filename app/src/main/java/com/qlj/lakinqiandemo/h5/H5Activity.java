package com.qlj.lakinqiandemo.h5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

/**
 * Created by Administrator on 2018/10/31.
 */

public class H5Activity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        initView();
    }

    private void initView() {
        mWebView = findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new JsInteration(), "android");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("http://www.baidu.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
