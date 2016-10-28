package com.lk.comecrazy.home.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/22 0022 16:27 .
 * 备注：
 */
@Table(name = "collect")
public class CollectBean {
    @Column(name = "_id", isId = true)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;
    @Column(name = "video")
    private String video;
    @Column(name = "logo")
    private String logo;

    public CollectBean() {
    }

    public CollectBean(String name, String city, String video, String logo) {
        this.name = name;
        this.city = city;
        this.video = video;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
