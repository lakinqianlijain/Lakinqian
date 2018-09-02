package com.qlj.lakinqiandemo.json;

import com.alibaba.fastjson.JSON;
import com.qlj.lakinqiandemo.json.bean.CBAPlayer;
import com.qlj.lakinqiandemo.json.bean.ResultBean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/2.
 */

public class FastjsonAnalysis {
    public static ResultBean AnalysisResultBean(String result) {
        ResultBean resultBean = JSON.parseObject(result, ResultBean.class);
        return resultBean;
    }

    public static List<CBAPlayer> AnalysisCBAPlayerList(String playerJson) {
        List<CBAPlayer> players = JSON.parseArray(playerJson, CBAPlayer.class);
        return players;
    }
}
