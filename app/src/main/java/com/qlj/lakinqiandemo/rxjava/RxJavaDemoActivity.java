package com.qlj.lakinqiandemo.rxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaDemoActivity extends BaseActivity {
    private static final String TAG = "RxJavaDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        LoginImpl login = new LoginImpl();
        login.login("david", "123456");
        login.rxLogin("david", "123456");
    }

    public void rxJava(View view) {
        // 创建observable
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "开始通知事件");
                emitter.onNext("http://192.168.1.102:8080/getImage");
            }
        })
                .map(new Function<String, Bitmap>() {
            @Override
            public Bitmap apply(String s) throws Exception {
                Log.i(TAG, " url地址转化为 Bitmap:" + s);
                return downLoad(s);
            }
        }).flatMap(new Function<Bitmap, ObservableSource<Bitmap>>() {
            @Override
            public ObservableSource<Bitmap> apply(Bitmap bitmap) throws Exception {
                final List<Bitmap> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add(null);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        })

                .subscribe(new Observer<Bitmap>() {

            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(Bitmap bitmap) {
                Log.i(TAG, " 观察者接受通知  图片宽度  " + bitmap.getWidth() + "  图片高度  " + bitmap.getHeight());
            }
        });

    }

    public void rxJava2(View view) {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR); //增加了一个参数

        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream.subscribe(downstream);
    }

    private Bitmap downLoad(String s) {
        Log.i(TAG, "正在下载");
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }
}
