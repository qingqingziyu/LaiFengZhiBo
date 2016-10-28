package com.lk.comecrazy.mylive.view;

import android.content.Context;

/**
 * Created by Administrator on 2016/10/20.
 * 直播的视图接口
 */
public interface ILiveFragmentInfo {

    void keepImageIcon(String name, String url);

    Context getFragmentActivity(); // 获取上下文
}
