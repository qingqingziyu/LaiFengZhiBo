package com.lk.comecrazy.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class  BaseActivity extends AppCompatActivity {
    private Unbinder mBind;
    //private UMShareAPI mShareAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());

        //绑定黄油刀
        mBind = ButterKnife.bind(this);

        //可以更改默认的session启动时间,默认30秒
       // MobclickAgent.setSessionContinueMillis(100000);
        //日志加密.如果enable为true，SDK会对日志进行加密。加密模式可以有效防止网络攻击，提高数据安全性。
        //如果enable为false，SDK将按照非加密的方式来传输日志。
        //如果您没有设置加密模式，SDK的加密模式为false（不加密）。
      //  MobclickAgent.enableEncrypt(true);

        //关闭错误统计功能.默认开启
        //MobclickAgent.setCatchUncaughtExceptions(false);

        //初始化
       // mShareAPI = UMShareAPI.get(BaseActivity.this);

        //初始化布局
        initView();
    }

    protected abstract void initView();


    @Override
    protected void onResume() {
        super.onResume();
        //统计数据
    }

    @Override
    protected void onPause() {
        super.onPause();
        //统计数据
    }


    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            //解除黄油刀的绑定
            mBind.unbind();

        }


    }
}
