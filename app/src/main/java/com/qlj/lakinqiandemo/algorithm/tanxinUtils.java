package com.qlj.lakinqiandemo.algorithm;

/**
 * Created by Administrator on 2019/8/31.
 */

public class tanxinUtils {

    // 贪心法找钱——有1元、5元、10元、20元、100元、200元的钞票无穷多章。现使用这些钞票支付X元，最少需要多少张？
    public static int findMoney(){
        int[] RMB={200,100,20,10,5,1};//钞票金额
        int NUM = 6; // 6种面值
        int X = 628; //需要支付金额为628
        int count = 0;
        for (int i = 0; i < NUM; i++){
            int use = X / RMB[i]; //需要面额为RMB[i]的钞票use张
            count += use; //总计增加use张
            X = X - RMB[i]*use; //总额-使用RMB[i]已组成的金额，即剩余需要支付的金额
        }
        return count;
    }


}
