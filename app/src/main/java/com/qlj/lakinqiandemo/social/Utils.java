package com.qlj.lakinqiandemo.social;

import com.qlj.lakinqiandemo.social.bean.TestBean;
import com.qlj.lakinqiandemo.social.bean.TestMessageBean;
import com.qlj.lakinqiandemo.social.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2019/2/28.
 */

public class Utils {

    public static List<TestBean> getCommentMessageData() {
        List<TestBean> testBeanList = new ArrayList<>();
        TestBean testBean = new TestBean(1, "http://img95.699pic.com/element/40028/1996.png_860.png", "lakin", "Like this post", "11:20");
        TestBean testBean1 = new TestBean(1, "http://pic1.win4000.com/wallpaper/b/53d61c80b5a7c.jpg", "bibi", "Like this post", "2018.03.02");
        TestBean testBean2 = new TestBean(1, "http://m.360buyimg.com/pop/jfs/t23809/256/1721187036/214079/45bc2469/5b697030N37d0ed73.jpg", "lakin", "hi, bibi", "09:20");
        testBeanList.add(testBean);
        testBeanList.add(testBean1);
        testBeanList.add(testBean2);
        return testBeanList;
    }

    public static List<TestBean> getFollowMessageData() {
        List<TestBean> testBeanList = new ArrayList<>();
        TestBean testBean = new TestBean(2, "http://img95.699pic.com/element/40028/1996.png_860.png", "lakin", "Follow you", "11:20");
        TestBean testBean1 = new TestBean(2, "http://pic1.win4000.com/wallpaper/b/53d61c80b5a7c.jpg", "bibi", "Follow you", "2018.03.02");
        TestBean testBean2 = new TestBean(2, "http://m.360buyimg.com/pop/jfs/t23809/256/1721187036/214079/45bc2469/5b697030N37d0ed73.jpg", "lakin", "Follow you", "09:20");
        testBeanList.add(testBean);
        testBeanList.add(testBean1);
        testBeanList.add(testBean2);
        return testBeanList;
    }

    public static List<TestMessageBean> getConversationData() {
        User mine = getMine();
        User friend = getFriend();
        List<TestMessageBean> testMessageBeans = new ArrayList<>();
        TestMessageBean testMessageBean = new TestMessageBean(0, "hi,bibi", mine, "09:30", 0);
        TestMessageBean testMessageBean1 = new TestMessageBean(1, "hi,lakin", friend, "09:31", 0);
        TestMessageBean testMessageBean2 = new TestMessageBean(0, "where are you from", mine, "09:40", 0);
        TestMessageBean testMessageBean3 = new TestMessageBean(1, "I am from china", friend, "09:41", 0);
        TestMessageBean testMessageBean4 = new TestMessageBean(0, "how old are you", mine, "09:55", 0);
        TestMessageBean testMessageBean5 = new TestMessageBean(1, "22", friend, "09:59", 0);
        TestMessageBean testMessageBean6 = new TestMessageBean(0, "nice to meet you", mine, "12:10", 0);
        TestMessageBean testMessageBean7 = new TestMessageBean(1, "nice to meet you too", friend, "12:12", 0);

        testMessageBeans.add(testMessageBean);
        testMessageBeans.add(testMessageBean1);
        testMessageBeans.add(testMessageBean2);
        testMessageBeans.add(testMessageBean3);
        testMessageBeans.add(testMessageBean4);
        testMessageBeans.add(testMessageBean5);
        testMessageBeans.add(testMessageBean6);
        testMessageBeans.add(testMessageBean7);
        return testMessageBeans;
    }

    public static User getMine(){
        return new User("http://img95.699pic.com/element/40028/1996.png_860.png", "lakin");
    }

    public static User getFriend(){
        return new User("http://pic1.win4000.com/wallpaper/b/53d61c80b5a7c.jpg", "bibi");
    }

}
