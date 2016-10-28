package com.lk.comecrazy.mylive.presenter;

import com.lk.comecrazy.mylive.view.ILiveFragmentInfo;
import com.lk.comecrazy.myself.model.UserDbModel;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/20.
 */
public class LivePresenter {
    private UserDbModel mDbModel;

    private ILiveFragmentInfo mView;

    //获取Activity的对象
    public LivePresenter(ILiveFragmentInfo view) {
        mDbModel = new UserDbModel(view.getFragmentActivity());

        this.mView = view;
    }

    //保持头像
    public void keepIcon() {


        Map<String, String> map = mDbModel.read();
        String name = map.get("name");
        String url = map.get("url");

        mView.keepImageIcon(name, url);
    }


}
