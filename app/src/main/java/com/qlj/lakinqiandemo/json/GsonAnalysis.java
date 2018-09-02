package com.qlj.lakinqiandemo.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qlj.lakinqiandemo.json.bean.CBAPlayer;
import com.qlj.lakinqiandemo.json.bean.ResultBean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/2.
 */

public class GsonAnalysis {

    public static ResultBean AnalysisResultBean(String result) {
        Gson gson = new Gson();
        ResultBean resultBean = gson.fromJson(result, ResultBean.class);
        return resultBean;
    }

    public static List<CBAPlayer> AnalysisCBAPlayerList(String playerJson) {
        Gson gson = new Gson();
        List<CBAPlayer> players = gson.fromJson(playerJson, new TypeToken<List<CBAPlayer>>() {
        }.getType());

        return players;
    }
}
