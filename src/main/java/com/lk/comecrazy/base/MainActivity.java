package com.lk.comecrazy.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lk.comecrazy.R;
import com.lk.comecrazy.utils.FragmentFactroy;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.Log;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IBaseUiInfo {

    private RadioButton mButAttention;
    private RadioButton mButHome;
    private RadioButton mButMessage;
    private RadioButton mButMyself;
    private ImageView mImageLive;
    private UMShareAPI mShareAPI;
    private BasePresenter mPresenter;
    private RadioGroup mGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //初始化布局
        initView();
        //设置RadioButton里面小图标的大小
        setButtonIcon();
        mPresenter = new BasePresenter(this);
        //初始化
        mShareAPI = UMShareAPI.get(this);

        if (mPresenter.isLoginSucceed()) {

            mShareAPI.doOauthVerify(MainActivity.this, SHARE_MEDIA.SINA, umAuthListener);
            mShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.SINA, umAuthListener);
        }

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Log.i("MLOG", data.toString());
            if (data.get("screen_name") != null) {
                mPresenter.saveUserMsg(data.get("screen_name"), data.get("profile_image_url"));
            }

            // Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    //初始化布局
    protected void initView() {
        //关注
        mButAttention = (RadioButton) findViewById(R.id.but_attention);
        //主页
        mButHome = (RadioButton) findViewById(R.id.but_home);
        //消息
        mButMessage = (RadioButton) findViewById(R.id.but_message);
        //我的
        mButMyself = (RadioButton) findViewById(R.id.but_myself);
        //直播
        mImageLive = (ImageView) findViewById(R.id.image_live);
        mGroup = (RadioGroup) findViewById(R.id.ra_group);
        //设置监听事件
        setOnListener();

        //默认加载首页
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(0));
        fragmentTransaction.commit();

    }

    //设置监听
    private void setOnListener() {
        mButAttention.setOnClickListener(this);
        mImageLive.setOnClickListener(this);
        mButHome.setOnClickListener(this);
        mButMessage.setOnClickListener(this);
        mButMyself.setOnClickListener(this);
    }

    //设置RadioButton里面的图标
    private void setButtonIcon() {
        //定义底部标签图片大小
        Drawable iconAttention = getResources().getDrawable(R.drawable.selector_attention);
        iconAttention.setBounds(0, 0, 50, 50);
        mButAttention.setCompoundDrawables(null, iconAttention, null, null);
        //定义底部标签图片大小
        Drawable iconHome = getResources().getDrawable(R.drawable.selector_home);
        iconHome.setBounds(0, 0, 50, 50);
        mButHome.setCompoundDrawables(null, iconHome, null, null);
        //定义底部标签图片大小
        Drawable iconMessage = getResources().getDrawable(R.drawable.selector_message);
        iconMessage.setBounds(0, 0, 50, 50);
        mButMessage.setCompoundDrawables(null, iconMessage, null, null);
        //定义底部标签图片大小
        Drawable iconMyself = getResources().getDrawable(R.drawable.selector_myself);
        iconMyself.setBounds(0, 0, 50, 50);
        mButMyself.setCompoundDrawables(null, iconMyself, null, null);
    }


    //选择对应的Fragment
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.but_home:
                //动态添加主页的Fragment
                fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(0));
                break;
            case R.id.but_attention:
                //动态添加关注的Fragment
                fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(1));
                break;
            case R.id.image_live:
                //动态添加直播的Fragment
                fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(2)).addToBackStack(null);
                mGroup.setVisibility(View.GONE);
                mImageLive.setVisibility(View.GONE);

                break;
            case R.id.but_message:
                //动态添加消息的Fragment
                fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(3));
                break;
            case R.id.but_myself:
                //添加个人的Fragment
                fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(4));
                break;
        }
        fragmentTransaction.commit();
    }

    //返回HomeFragmet的事件监听
    @Override
    public void backListener() {
        mButHome.setChecked(true);

    }

    //显示布局
    @Override
    public void showView() {
        mGroup.setVisibility(View.VISIBLE);
        mImageLive.setVisibility(View.VISIBLE);
    }


    //处理返回键
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
            mGroup.setVisibility(View.VISIBLE);
            mImageLive.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }
}
