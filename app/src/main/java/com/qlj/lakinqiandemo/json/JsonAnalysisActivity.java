package com.qlj.lakinqiandemo.json;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.json.bean.CBAPlayer;
import com.qlj.lakinqiandemo.json.bean.ResultBean;

import java.util.ArrayList;
import java.util.List;

import static com.qlj.lakinqiandemo.utils.CommonUtil.TAG;

/**
 * Created by Administrator on 2018/9/2.
 */

public class JsonAnalysisActivity extends BaseActivity implements View.OnClickListener {
    private String string = "{\"code\":200,\"player\":[{\"name\":\"易建联\",\"age\":31,\"height\":\"213cm\",\"weight\":\"116kg\",\"team\":\"广东东莞银行队\",\"birthplace\":\"广东省鹤山市\",\"position\":\"大前锋\"},{\"name\":\"周琦\",\"age\":22,\"height\":\"216cm\",\"weight\":\"95kg\",\"team\":\"休斯敦火箭队\",\"birthplace\":\"河南新乡\",\"position\":\"中锋\"},{\"name\":\"阿不都沙拉木·阿不都热西提\",\"age\":22,\"height\":\"203cm\",\"weight\":\"89kg\",\"team\":\"新疆广汇飞虎俱乐部\",\"birthplace\":\"新疆阿勒泰\",\"position\":\"小前锋\"},{\"name\":\"郭艾伦\",\"age\":25,\"height\":\"192cm\",\"weight\":\"85kg\",\"team\":\"辽宁衡业飞豹\",\"birthplace\":\"辽宁省辽阳市\",\"position\":\"控球后卫\"},{\"name\":\"赵继伟\",\"age\":23,\"height\":\"185cm\",\"weight\":\"77kg\",\"team\":\"辽宁衡业飞豹俱乐部\",\"birthplace\":\"辽宁省海城市\",\"position\":\"控球后卫\"},{\"name\":\"丁彦雨航\",\"age\":25,\"height\":\"201cm\",\"weight\":\"98kg\",\"team\":\"达拉斯独行侠队\",\"birthplace\":\"新疆克拉玛依\",\"position\":\"控球后卫\"}],\"msg\":\"Success\"}";

    private TextView mAndroid, mGson, mFastJson;
    private ResultBean mResultBean;
    private List<CBAPlayer> players = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_analysis);
        initViews();
    }

    private void initViews() {
        mAndroid = findViewById(R.id.android_json);
        mAndroid.setOnClickListener(this);
        mGson = findViewById(R.id.gson_json);
        mGson.setOnClickListener(this);
        mFastJson = findViewById(R.id.fast_json);
        mFastJson.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.android_json:
                mResultBean = NativeJson.AnalysisResultBean(string);
                players = NativeJson.AnalysisCBAPlayerList(mResultBean.getPlayer());
                showToast();
                break;
            case R.id.gson_json:
//                mResultBean = GsonAnalysis.AnalysisResultBean(string);
                mResultBean = NativeJson.AnalysisResultBean(string);
                players = GsonAnalysis.AnalysisCBAPlayerList(mResultBean.getPlayer());
                showToast();
                break;
            case R.id.fast_json:
                mResultBean = FastjsonAnalysis.AnalysisResultBean(string);
                players = FastjsonAnalysis.AnalysisCBAPlayerList(mResultBean.getPlayer());
                showToast();
                break;
        }
    }

    public void showToast() {
        if (players == null || players.size() == 0) {
            return;
        }
        for (CBAPlayer player : players) {
            Log.e(TAG, "player: " + player.getName());
        }
        Toast.makeText(this, "解析成功", Toast.LENGTH_SHORT).show();
    }
}
