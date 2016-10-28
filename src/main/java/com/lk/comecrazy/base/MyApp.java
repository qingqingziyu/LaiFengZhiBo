package com.lk.comecrazy.base;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import io.vov.vitamio.Vitamio;
import okhttp3.OkHttpClient;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        iniOkHttp();
        //xutils初始化
        x.Ext.init(this);
        x.Ext.setDebug(true);
        //vitamio初始化
        Vitamio.isInitialized(this);
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        Config.REDIRECT_URL = "www.baidu.com";
    }

    private void iniOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient();
    }
}
