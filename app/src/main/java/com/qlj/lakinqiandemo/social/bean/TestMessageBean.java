package com.qlj.lakinqiandemo.social.bean;

/**
 * Created by lakinqian on 2019/2/28.
 */

public class TestMessageBean {

    int type; // 0:发送， 1:接收
    String content;
    User sendUser;
    String sendTime;
    int status;

    public TestMessageBean(int type, String content, User user, String sendTime, int status){
        this.type = type;
        this.content = content;
        this.sendUser = user;
        this.sendTime = sendTime;
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSendUser() {
        return sendUser;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
