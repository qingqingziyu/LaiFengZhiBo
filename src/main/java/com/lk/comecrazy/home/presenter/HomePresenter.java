package com.lk.comecrazy.home.presenter;

import android.view.View;

import com.lk.comecrazy.home.view.IHomeFragment;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/16 0016 15:15 .
 * 备注：
 */

public class HomePresenter {
    private IHomeFragment mView;
    private View HomeView;
    public HomePresenter(IHomeFragment view, View HomeView) {
        this.mView = view;
        this.HomeView=HomeView;
    }

}

