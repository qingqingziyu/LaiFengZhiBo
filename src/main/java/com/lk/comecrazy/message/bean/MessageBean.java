package com.lk.comecrazy.message.bean;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/21 0021 16:50 .
 * 备注：
 */

public class MessageBean {
    private String title;
    private String content;
    private int logo;

    public MessageBean() {
    }

    public MessageBean(String title, String content, int logo) {
        this.title = title;
        this.content = content;
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
