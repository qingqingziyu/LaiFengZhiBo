package com.lk.comecrazy.base;

import android.content.Context;

import com.lk.comecrazy.myself.model.UserDbModel;

/**
 * Created by Administrator on 2016/10/15.
 */
public class BasePresenter {

    IBaseUiInfo mView;
    private UserDbModel mDbModel;

    public BasePresenter(IBaseUiInfo view) {
        this.mView = view;
        mDbModel = new UserDbModel((Context) view);
    }

    //返回首页
    public void backHome() {
        mView.backListener();

    }

    //判断数据库中是否为空
    public boolean isLoginSucceed() {
        if (mDbModel.isDBNull()) {
            return true;
        }
        return false;
    }

    //显示Activity的视图
    public void showActivityView() {
        mView.showView();
    }

    //存储数据
    public void saveUserMsg(String name, String url) {
        mDbModel.insert(name, url);
    }
}