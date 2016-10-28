package com.lk.comecrazy.myself.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lk.comecrazy.R;
import com.lk.comecrazy.myself.presenter.MyselfFragmentPresenter;
import com.lk.comecrazy.utils.GlideRoundTransform;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * <p/>
 * 个人中心的Fragment
 */
public class MyselfFragment extends Fragment implements View.OnClickListener, IMyselfInfo {


    @BindView(R.id.myself_icon)
    ImageView mMyselfIcon;
    @BindView(R.id.myself_name)
    TextView mMyselfName;
    private RelativeLayout mIconLayout;
    private RelativeLayout mMoneyLayout;
    private RelativeLayout mSettingLayout;
    private RelativeLayout mAttentionLayout;
    private MyselfFragmentPresenter mPresenter;
    private Unbinder mBind;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myself, container, false);
        //初始化布局
        initView(view);
        mPresenter = new MyselfFragmentPresenter(this);
        mBind = ButterKnife.bind(this, view);
        return view;
    }

    //初始化布局
    private void initView(View view) {
        mIconLayout = (RelativeLayout) view.findViewById(R.id.myself_icon_layout);
        mMoneyLayout = (RelativeLayout) view.findViewById(R.id.myself_money_layout);
        mSettingLayout = (RelativeLayout) view.findViewById(R.id.myself_setting_layout);
        mAttentionLayout = (RelativeLayout) view.findViewById(R.id.myself_attention_layout);


        //设置事件监听
        setListener();
    }

    //设置监听事件
    private void setListener() {
        mIconLayout.setOnClickListener(this);
        mMoneyLayout.setOnClickListener(this);
        mSettingLayout.setOnClickListener(this);
        mAttentionLayout.setOnClickListener(this);
    }

    //跳转到对应的activity
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myself_icon_layout: //个人头像界面
                startActivity(new Intent(getActivity(), UserActivity.class));
                break;
            case R.id.myself_attention_layout: //关注界面
                startActivity(new Intent(getActivity(), AttentionActivity.class));
                break;
            case R.id.myself_money_layout: //钱包支付界面
                startActivity(new Intent(getActivity(), MoneyActivity.class));
                break;
            case R.id.myself_setting_layout: //设置退出界面
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //   mIconLayout.getFocusedChild();
        //保存图标
        mPresenter.keepIcon();
    }

    //保持头像
    @Override
    public void keepImageIcon(String name, String url) {
        if (name != null && url != null) {
            mMyselfName.setText(name);
            Glide.with(this).load(url).transform(new GlideRoundTransform(getFragmentActivity())).into(mMyselfIcon);
        }
    }

    //获取Activity
    @Override
    public Context getFragmentActivity() {
        return getActivity();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            mBind.unbind();
        }
    }
}
