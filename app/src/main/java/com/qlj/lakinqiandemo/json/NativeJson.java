package com.qlj.lakinqiandemo.json;

import com.qlj.lakinqiandemo.json.bean.CBAPlayer;
import com.qlj.lakinqiandemo.json.bean.ResultBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/2.
 */

public class NativeJson {

    public static ResultBean AnalysisResultBean(String result) {
        ResultBean resultBean = new ResultBean();
        try {
            JSONObject configJs = new JSONObject(result);
            int code = configJs.getInt("code");
            String player = configJs.getString("player");
            String msg = configJs.getString("msg");
            resultBean.setCode(code);
            resultBean.setPlayer(player);
            resultBean.setMsg(msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultBean;
    }

    public static CBAPlayer AnalysisCBAPlayer(String playerJson) {
        CBAPlayer player = new CBAPlayer();
        try {
            JSONObject configJs = new JSONObject(playerJson);
            String name = configJs.getString("name");
            int age = configJs.getInt("age");
            String height = configJs.getString("height");
            String weight = configJs.getString("weight");
            String team = configJs.getString("team");
            String birthplace = configJs.getString("birthplace");
            String position = configJs.getString("position");
            player.setName(name);
            player.setAge(age);
            player.setHeight(height);
            player.setWeight(weight);
            player.setTeam(team);
            player.setBirthplace(birthplace);
            player.setPosition(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return player;
    }

    public static List<CBAPlayer> AnalysisCBAPlayerList(String playerJson) {
        List<CBAPlayer> cbaPlayerList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(playerJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CBAPlayer player = new CBAPlayer();
                if (jsonObject != null) {
                    String name = jsonObject.getString("name");
                    int age = jsonObject.getInt("age");
                    String height = jsonObject.getString("height");
                    String weight = jsonObject.getString("weight");
                    String team = jsonObject.getString("team");
                    String birthplace = jsonObject.getString("birthplace");
                    String position = jsonObject.getString("position");
                    player.setName(name);
                    player.setAge(age);
                    player.setHeight(height);
                    player.setWeight(weight);
                    player.setTeam(team);
                    player.setBirthplace(birthplace);
                    player.setPosition(position);
                    cbaPlayerList.add(player);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cbaPlayerList;
    }
}
