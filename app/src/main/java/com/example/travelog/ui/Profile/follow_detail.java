package com.example.travelog.ui.Profile;

public class follow_detail {
    String star;//要关注的用户名
    String fans;//自己的用户名
    String time;//关注时间

    public follow_detail(String star, String fans, String time) {
        this.star = star;
        this.fans = fans;
        this.time = time;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
