package com.qlj.lakinqiandemo.hook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.hook.proxy.Civilian;
import com.qlj.lakinqiandemo.hook.proxy.DynamicProxy;
import com.qlj.lakinqiandemo.hook.proxy.ILawsuit;
import com.qlj.lakinqiandemo.hook.proxy.Lawyer;

import java.lang.reflect.Proxy;

/**
 * Created by lakinqian on 2018/5/30.
 */

public class HookActivity extends AppCompatActivity implements View.OnClickListener  {
    Button mStaticProxy, mDynamicProxy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        initView();
    }

    private void initView() {
        mStaticProxy = findViewById(R.id.static_proxy);
        mStaticProxy.setOnClickListener(this);
        mDynamicProxy = findViewById(R.id.dynamic_proxy);
        mDynamicProxy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.static_proxy:
                ILawsuit civilian = new Civilian();
                ILawsuit lawyer = new Lawyer(civilian);
                lawyer.submit();
                lawyer.burden();
                lawyer.defend();
                lawyer.finish();
                break;
            case R.id.dynamic_proxy:
                ILawsuit lawsuit = new Civilian();
                DynamicProxy proxy = new DynamicProxy(lawsuit);
                ClassLoader loader = lawsuit.getClass().getClassLoader();
                //动态创建代理类，需要传入一个类加载器ClassLoader；一个你希望这个代理实现的接口列表，这里要代理ILawsuit接口；
                //和一个InvocationHandler的实现，也就是前面创建的proxy。
                ILawsuit ilawyer = (ILawsuit) Proxy.newProxyInstance(loader, new Class[]{ILawsuit.class}, proxy);
                ilawyer.submit();
                ilawyer.burden();
                ilawyer.defend();
                ilawyer.finish();
                break;


        }
    }
}
