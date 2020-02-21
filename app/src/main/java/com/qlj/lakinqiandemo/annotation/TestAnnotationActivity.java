package com.qlj.lakinqiandemo.annotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.lakinqian.inject.InjectView;
import com.example.lakinqian.inject_annotion.BindView;
import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.annotation.runtimeAnnotations.OnClickRun;
import com.qlj.lakinqiandemo.annotation.runtimeAnnotations.ViewBinderRun;
import com.qlj.lakinqiandemo.annotation.runtimeAnnotations.ViewBinderParser;

public class TestAnnotationActivity extends BaseActivity {
    @ViewBinderRun(id = R.id.button1)
    public Button button1;

   @OnClickRun(id = R.id.button1)
    public void button1OnClick() {
       Toast.makeText(this, "运行时注解button1 成功", Toast.LENGTH_SHORT).show();
   }

   @BindView(value = R.id.button2)
   public Button button2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ViewBinderParser.inject(TestAnnotationActivity.this);
        InjectView.bind(this);
        showCompileTimeAnnotation();
    }

    private void showCompileTimeAnnotation() {
        if (button2 != null){
            Toast.makeText(this, "编译时注解button2 成功", Toast.LENGTH_SHORT).show();
        }
    }
}
