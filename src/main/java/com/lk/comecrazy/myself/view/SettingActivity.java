package com.lk.comecrazy.myself.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;

import com.lk.comecrazy.R;
import com.lk.comecrazy.login.LoginActivity;
import com.lk.comecrazy.myself.presenter.SettingPresenter;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class SettingActivity extends AppCompatActivity implements ISettingInfo {

    private CheckBox mCheckAttention;
    private CheckBox mCheckGift;
    private CheckBox mCheckMessage;
    private Toolbar mBackBar;
    private UMShareAPI umShareAPI;
    private SettingPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        //初始化布局
        inintView();
        //设置checkbox的图标大小
        setCheckIcon();
        //设置图标返回键
        setIconBack();
    }

    //设置toolbar图标的返回事件监听
    private void setIconBack() {
        mBackBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出当前Activity
                finish();
            }
        });

    }

    //设置checkbox的图标大小
    private void setCheckIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.selector_setting);
        drawable.setBounds(0, 0, 80, 60);
        mCheckAttention.setCompoundDrawables(null, null, drawable, null);
        mCheckGift.setCompoundDrawables(null, null, drawable, null);
        mCheckMessage.setCompoundDrawables(null, null, drawable, null);

        mPresenter = new SettingPresenter(this);


    }

    //初始化布局
    private void inintView() {

        mCheckAttention = (CheckBox) findViewById(R.id.setting_check_attention);
        mCheckGift = (CheckBox) findViewById(R.id.setting_check_gift);
        mCheckMessage = (CheckBox) findViewById(R.id.setting_check_message);
        mBackBar = (Toolbar) findViewById(R.id.setting_toolbar);
        umShareAPI = UMShareAPI.get(this);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    };

    //TODO:实现退出登录跳转到登录界面,如果是第三方登录，就解除授权
    // 退出按钮事件监听
    public void exit(View view) {
        Toast.makeText(SettingActivity.this, "退出登录", Toast.LENGTH_SHORT).show();
        umShareAPI.deleteOauth(this, SHARE_MEDIA.SINA, umAuthListener);
        //清空数据
        mPresenter.deletedUser();
        //跳转到登录界面
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }


}
