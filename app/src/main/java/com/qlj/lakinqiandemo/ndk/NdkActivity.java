package com.qlj.lakinqiandemo.ndk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;

public class NdkActivity extends BaseActivity implements View.OnClickListener {
    private Button mButton;

    private Button btnAdd,btnSub,btnMul,btnDiv;
    private EditText inputA,inputB;
    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk);
        initViews();
    }

    private void initViews() {
        mButton = findViewById(R.id.bt_ndk_study);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = NdkTools.getStringFromNDK();
                Toast.makeText(NdkActivity.this, "通过NDK获取对应的String:"+text, Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd=this.findViewById(R.id.add);
        btnDiv=this.findViewById(R.id.div);
        btnMul=this.findViewById(R.id.mul);
        btnSub=this.findViewById(R.id.sub);

        inputA=this.findViewById(R.id.inputa);
        inputB=this.findViewById(R.id.inputb);

        tvResult=this.findViewById(R.id.result);

        btnAdd.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnSub.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        double result=0;
        String strA=inputA.getText().toString();
        String strB=inputB.getText().toString();
        if (TextUtils.isEmpty(strA) || TextUtils.isEmpty(strB)) return;
        int a=Integer.parseInt(strA);
        int b=Integer.parseInt(strB);
        int id = view.getId();
        switch (id){
            case R.id.add:
                result = DynamicNdkTools.add(a,b);
                break;
            case R.id.div:
                result = DynamicNdkTools.div(a,b);
                break;
            case R.id.mul:
                result = DynamicNdkTools.mul(a,b);
                break;
            case R.id.sub:
                result = DynamicNdkTools.sub(a,b);
                break;
        }
        tvResult.setText(""+result);
    }
}
