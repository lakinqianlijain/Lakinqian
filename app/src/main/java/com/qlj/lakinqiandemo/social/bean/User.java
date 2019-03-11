package com.qlj.lakinqiandemo.social.bean;

/**
 * Created by lakinqian on 2019/2/28.
 */

public class User {
    String avatar;
    String nickname;

    public User (String avatar, String nickname){
        this.avatar = avatar;
        this.nickname = nickname;
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
}
