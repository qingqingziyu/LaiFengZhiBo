package com.lk.comecrazy.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础的Fragment ,其他Fragment 继承该Fragment
 */
public abstract class BaseFragment extends Fragment {

    private Context mContext;
    private Unbinder mBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getlayoutId(), container, false);

        mBind = ButterKnife.bind(view);
        //初始化Fragment
        initView();
        return view;
    }

    protected abstract void initView();

    public abstract int getlayoutId();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBind != null) {
            //解除绑定
            mBind.unbind();
        }
    }
}
