package com.lk.comecrazy.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.lk.comecrazy.R;
import com.lk.comecrazy.base.BasePresenter;
import com.lk.comecrazy.base.IBaseUiInfo;
import com.lk.comecrazy.base.MainActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends AppCompatActivity implements IBaseUiInfo {

    @BindView(R.id.login_image_weixin)
    ImageView mLoginWeixin;
    @BindView(R.id.login_image_qq)
    ImageView mLoginQq;
    @BindView(R.id.login_image_sina)
    ImageView mLoginSina;
    @BindView(R.id.login_image_come_crazy)
    ImageView mLoginComeCrazy;
    private Unbinder mBind;
    private UMShareAPI mShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mBind = ButterKnife.bind(this);
        BasePresenter mPresenter = new BasePresenter(this);
       // boolean loginSucceed = mPresenter.isLoginSucceed();
        //初始化友盟
        mShareAPI = UMShareAPI.get(this);
        //判断
        if (!mPresenter.isLoginSucceed()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    @OnClick({R.id.login_image_weixin, R.id.login_image_qq, R.id.login_image_sina, R.id.login_image_come_crazy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_image_weixin: //微信登录

                mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);

                break;
            case R.id.login_image_qq: //qq登录
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.login_image_sina: //新浪登录
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
                break;
            case R.id.login_image_come_crazy://来疯登录

                break;
        }
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

          //  Log.i("MLOG", data.toString());

            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "取消登录", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            //解除绑定
            mBind.unbind();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void backListener() {

    }

    @Override
    public void showView() {

    }


}
