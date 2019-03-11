package com.qlj.lakinqiandemo.social.bean;

/**
 * Created by lakinqian on 2019/2/28.
 */

public class TestBean {
    int type; // 1:Like;2:Follow;3:Warning;4:message
    String avatar;
    String nickname;
    String content;
    String sendTime;
    int noReadCount;

    public TestBean(int type, String avatar, String nickname, String content, String sendTime){
        this.type = type;
        this.avatar = avatar;
        this.nickname = nickname;
        this.content = content;
        this.sendTime = sendTime;
    }

    public TestBean(int type, String avatar, String nickname, String content, String sendTime, int noReadCount){
        this.type = type;
        this.avatar = avatar;
        this.nickname = nickname;
        this.content = content;
        this.sendTime = sendTime;
        this.noReadCount = noReadCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getNoReadCount() {
        return noReadCount;
    }

    public void setNoReadCount(int noReadCount) {
        this.noReadCount = noReadCount;
    }
}
