package com.lk.comecrazy.myself.presenter;

import android.content.Context;

import com.lk.comecrazy.myself.model.UserDbModel;
import com.lk.comecrazy.myself.view.ISettingInfo;

/**
 * Created by Administrator on 2016/10/24.
 */
public class SettingPresenter {

    private UserDbModel dbModel;

    private ISettingInfo mView;

    //初始化
    public SettingPresenter(ISettingInfo view) {
        dbModel = new UserDbModel((Context) view);
        this.mView = view;
    }

    //清空账号信息
    public void deletedUser() {
        dbModel.deleteTable();
    }
}
