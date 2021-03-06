package com.qlj.lakinqiandemo;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qlj.lakinqiandemo.bean.Student;
import com.qlj.lakinqiandemo.binder.BinderActivity;
import com.qlj.lakinqiandemo.common.notification.NotificationHelper;
import com.qlj.lakinqiandemo.contralayout.ContralayoutActivity;
import com.qlj.lakinqiandemo.eventbus.EventbusActivity;
import com.qlj.lakinqiandemo.h5.H5Activity;
import com.qlj.lakinqiandemo.hook.HookActivity;
import com.qlj.lakinqiandemo.json.JsonAnalysisActivity;
import com.qlj.lakinqiandemo.json.bean.CBAPlayer;
import com.qlj.lakinqiandemo.mvp.login.view.LoginActivity;
import com.qlj.lakinqiandemo.ndk.NdkActivity;
import com.qlj.lakinqiandemo.optimize.MemoryOptimizeActivity;
import com.qlj.lakinqiandemo.reflection.ReflectionActivity;
import com.qlj.lakinqiandemo.service.JobServiceHelper;
import com.qlj.lakinqiandemo.share.ShareBottomDialog;
import com.qlj.lakinqiandemo.share.ShareUtils;
import com.qlj.lakinqiandemo.utils.JumpActivityUtil;
import com.qlj.lakinqiandemo.video.MediaEdit.MediaEditActivity;
import com.qlj.lakinqiandemo.video.VideoActivity;
import com.qlj.lakinqiandemo.views.CustomizeViewActivity;
import com.qlj.lakinqiandemo.views.SlideViewActivity;
import com.qlj.lakinqiandemo.views.animation.LoadingActivity;
import com.qlj.lakinqiandemo.views.canvas.CanvasActivity;
import com.qlj.lakinqiandemo.views.loadingView.LoadingViewActivity;
import com.qlj.lakinqiandemo.views.lottie.LottieActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.qlj.lakinqiandemo.service.JobServiceHelper.SHOW_NOTIFICATION;
import static com.qlj.lakinqiandemo.share.ShareBottomDialog.FACEBOOK;
import static com.qlj.lakinqiandemo.share.ShareBottomDialog.WHATAPP;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    Button mReflection, mHOOK, mMVP, mAnimation, mContralayout,
            mLockPage, mVideoPlayPage, mJson, mLottieAnim, mEventBus, mH5;

    private NotificationHelper helper;

    // todo 沉浸状态栏总结  https://blog.csdn.net/u014418171/article/details/81223681

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mReflection = findViewById(R.id.reflection);
        mReflection.setOnClickListener(this);
        mHOOK = findViewById(R.id.hook);
        mHOOK.setOnClickListener(this);
        mMVP = findViewById(R.id.mvp);
        mMVP.setOnClickListener(this);
        mAnimation = findViewById(R.id.animation);
        mAnimation.setOnClickListener(this);
        mContralayout = findViewById(R.id.contralayout);
        mContralayout.setOnClickListener(this);
        mLockPage = findViewById(R.id.lock_page);
        mLockPage.setOnClickListener(this);
        mVideoPlayPage = findViewById(R.id.video_play);
        mVideoPlayPage.setOnClickListener(this);
        mJson = findViewById(R.id.json_analysis);
        mJson.setOnClickListener(this);
        mLottieAnim = findViewById(R.id.bt_lottie);
        mLottieAnim.setOnClickListener(this);
        mEventBus = findViewById(R.id.bt_event_bus);
        mEventBus.setOnClickListener(this);
        mH5 = findViewById(R.id.bt_h5);
        mH5.setOnClickListener(this);
        findViewById(R.id.bt_slide_view).setOnClickListener(this);
        findViewById(R.id.bt_memory_optimization).setOnClickListener(this);
        findViewById(R.id.bt_file_related).setOnClickListener(this);
        findViewById(R.id.bt_banner).setOnClickListener(this);
        findViewById(R.id.bt_share).setOnClickListener(this);
        findViewById(R.id.bt_social).setOnClickListener(this);
        findViewById(R.id.bt_annotation).setOnClickListener(this);
        findViewById(R.id.bt_canvas).setOnClickListener(this);
        findViewById(R.id.bt_binder).setOnClickListener(this);
        findViewById(R.id.bt_loading_view).setOnClickListener(this);
        findViewById(R.id.bt_media_edit).setOnClickListener(this);
        findViewById(R.id.bt_ndk).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reflection:
                JumpActivityUtil.JumpSelfActivity(this, ReflectionActivity.class);
                break;
            case R.id.hook:
                JumpActivityUtil.JumpSelfActivity(this, HookActivity.class);
                break;
            case R.id.mvp:
                JumpActivityUtil.JumpSelfActivity(this, LoginActivity.class);
                break;
            case R.id.animation:
                JumpActivityUtil.JumpSelfActivity(this, LoadingActivity.class);
                break;
            case R.id.contralayout:
                JumpActivityUtil.JumpSelfActivity(this, ContralayoutActivity.class);
                break;
            case R.id.lock_page:
                JumpActivityUtil.JumpSelfActivity(this, CustomizeViewActivity.class);
                break;
            case R.id.video_play:
                JumpActivityUtil.JumpSelfActivity(this, VideoActivity.class);
                break;
            case R.id.json_analysis:
                JumpActivityUtil.JumpSelfActivity(this, JsonAnalysisActivity.class);
                break;
            case R.id.bt_lottie:
                JumpActivityUtil.JumpSelfActivity(this, LottieActivity.class);
                break;
            case R.id.bt_event_bus:
                JumpActivityUtil.JumpSelfActivity(this, EventbusActivity.class);
                break;
            case R.id.bt_h5:
                JumpActivityUtil.JumpSelfActivity(this, H5Activity.class);
                break;
            case R.id.bt_slide_view:
                JumpActivityUtil.JumpSelfActivity(this, SlideViewActivity.class);
                break;
            case R.id.bt_memory_optimization:
                JumpActivityUtil.JumpSelfActivity(this, MemoryOptimizeActivity.class);
                break;
            case R.id.bt_file_related:
//                JumpActivityUtil.JumpSelfActivity(this, FileActivity.class);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //移除标记为id的通知 (只是针对当前Context下的所有Notification)
                notificationManager.cancel(10000);
                //移除所有通知
                break;
            case R.id.bt_banner:
//                JumpActivityUtil.JumpSelfActivity(this, BannerActivity.class);
//                if (helper == null) {
//                    helper = new NotificationHelper(this);
//                }
//                helper.showNotification();
//                TimeTaskUtil.startAlarmSchedule(this, TimeTaskUtil.TIME_TASK_ACTION, 10001, System.currentTimeMillis() + 10 * 1000, 10 * 1000);

                JobServiceHelper.setJobScheduler(this, SHOW_NOTIFICATION, System.currentTimeMillis(), 20 * 1000, null);

                break;
            case R.id.bt_share:
                ShareBottomDialog dialog = new ShareBottomDialog(this, R.style.dialog);
                dialog.setItemClickListener(new ShareBottomDialog.OnItemClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int position) {
                        switch (position) {
                            case FACEBOOK:
                                ShareUtils.shareLinkFacebook(MainActivity.this);
                                break;
                            case WHATAPP:
                                ShareUtils.shareLink(MainActivity.this, ShareUtils.WHATSAPP_PACKAGE_NAME);
                                break;
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.bt_social:
                Intent intent = new Intent();
                intent.setClassName("com.tct.video", "com.tct.video.View.activities.LocalVideoPlayActivity");
                intent.putExtra("playPath", "/storage/emulated/0/DCIM/Camera/VID_20190514_131548.mp4");
                this.startActivity(intent);

                List<Student> students = new ArrayList<>();
                Student student = new Student("lala", 12);
                Student student1 = new Student("lalala", 13);
                Student student2 = new Student("lalalala", 14);
                Student student3 = new Student("lalalalala", 15);
                Student student4 = new Student("lalalalalala", 16);
                students.add(student);
                students.add(student1);
                students.add(student2);
                students.add(student3);
                students.add(student4);

                Student qqq = null;
                Iterator<Student> iterator = students.iterator();
                while (iterator.hasNext()) {
                    Student s = iterator.next();
                    if (s.getName().equals("lalalala")) {
                        s.setName("la");
                        qqq = s;
                        iterator.remove();
//                        students.remove(s);
//                        students.add(0, students.remove(students.indexOf(s)));
                    }
                }
                students.add(0, qqq);

//                for (Student s : students){
//                    if (s.getName().equals("lalalala")){
//                        s.setName("la");
////                        students.remove(s);
//                        students.add(0, students.remove(students.indexOf(s)));
//                    }
//                }
                for (Student s : students) {
                    Log.e("6666", "onClick: " + s.getName());
                }
//                JumpActivityUtil.JumpSelfActivity(this, ScanActivity.class);
                break;
            case R.id.bt_annotation:
                Toast.makeText(this, "注解请查看另一个InjectDemo", Toast.LENGTH_SHORT).show();
//                JumpActivityUtil.JumpSelfActivity(this, TestAnnotationActivity.class);
                break;
            case R.id.bt_canvas:
                JumpActivityUtil.JumpSelfActivity(this, CanvasActivity.class);
                break;
            case R.id.bt_binder:
                JumpActivityUtil.JumpSelfActivity(this, BinderActivity.class);
                break;
            case R.id.bt_loading_view:
                JumpActivityUtil.JumpSelfActivity(this, LoadingViewActivity.class);
                break;
            case R.id.bt_media_edit:
                JumpActivityUtil.JumpSelfActivity(this, MediaEditActivity.class);
                break;
            case R.id.bt_ndk:
                JumpActivityUtil.JumpSelfActivity(this, NdkActivity.class);
                break;

        }
    }


    public class DynamicProxy implements InvocationHandler {//实现InvocationHandler接口
        private Object obj;//被代理的对象

        public DynamicProxy(Object obj) {
            this.obj = obj;
        }

        //重写invoke()方法
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("海外动态代理调用方法： " + method.getName());
            Object result = method.invoke(obj, args);//调用被代理的对象的方法
            return result;
        }
    }

    public void test() {
        CBAPlayer domestic = new CBAPlayer();                                 //创建国内购买人
        DynamicProxy proxy = new DynamicProxy(domestic);                  //创建动态代理
        ClassLoader classLoader = domestic.getClass().getClassLoader();   //获取ClassLoader
        CBAPlayer oversea = (CBAPlayer) Proxy.newProxyInstance(classLoader, new Class[]{CBAPlayer.class}, proxy); //通过 Proxy 创建海外代购实例 ，实际上通过反射来实现的。

    }

}
