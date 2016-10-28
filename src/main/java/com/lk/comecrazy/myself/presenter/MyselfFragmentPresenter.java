package com.lk.comecrazy.myself.presenter;

import com.lk.comecrazy.myself.model.UserDbModel;
import com.lk.comecrazy.myself.view.IMyselfInfo;

import java.util.Map;

/**
 * Created by Administrator on 2016/10/21.
 */
public class MyselfFragmentPresenter {
    private UserDbModel mDbModel;

    private IMyselfInfo mView;

    //获取Activity的对象
    public MyselfFragmentPresenter(IMyselfInfo view) {
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
