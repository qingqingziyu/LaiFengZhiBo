package com.lk.comecrazy.message.bean;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/21 0021 21:43 .
 * 备注：
 */

public class ZiXunBean {
    private int logo;
    private String content;

    public ZiXunBean() {
    }

    public ZiXunBean(String content, int logo) {
        this.content = content;
        this.logo = logo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
