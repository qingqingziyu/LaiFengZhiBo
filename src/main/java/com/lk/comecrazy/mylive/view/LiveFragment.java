package com.lk.comecrazy.mylive.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lk.comecrazy.R;
import com.lk.comecrazy.base.BasePresenter;
import com.lk.comecrazy.base.MainActivity;
import com.lk.comecrazy.mylive.presenter.LivePresenter;
import com.lk.comecrazy.utils.GlideRoundTransform;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

/**
 * A simple {@link Fragment} subclass.
 * 我的直播Fragment
 */
public class LiveFragment extends Fragment implements ILiveFragmentInfo, View.OnClickListener {


    private ImageView mImageBack;
    private CheckBox mCamera;
    private CheckBox mBeauty;
    private ImageView mIcon;
    private LivePresenter mLivePresenter;
    private ImageView mShare;
    private EditText mEditShare;
    private Button mLiveStart;
    private BasePresenter mBaspresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化布局
        initView(view);
        mLivePresenter = new LivePresenter(this);
        mBaspresenter = new BasePresenter((MainActivity) getFragmentActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        //保存图标
        mLivePresenter.keepIcon();
    }

    //初始化直播fragment里面的布局
    private void initView(View view) {
        mImageBack = (ImageView) view.findViewById(R.id.live_back);

        mIcon = (ImageView) view.findViewById(R.id.live_image_icon);
        mShare = (ImageView) view.findViewById(R.id.live_image_share);
        mEditShare = (EditText) view.findViewById(R.id.live_edit_share);
        mLiveStart = (Button) view.findViewById(R.id.live_start_btn);
        mImageBack.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mLiveStart.setOnClickListener(this);

    }

    @Override
    public void keepImageIcon(String name, String url) {
        if (name != null && url != null) {

            Glide.with(this).load(url).transform(new GlideRoundTransform(getActivity())).into(mIcon);
        }
    }


    //获取上下文
    @Override
    public Context getFragmentActivity() {
        return getActivity();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_back: //退出直播
                //退出当前的Fragment
                getFragmentManager().popBackStack();
                mBaspresenter.showActivityView();
                break;
            case R.id.live_image_share: //分享

                SHARE_MEDIA[] list = new SHARE_MEDIA[]{SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS, SHARE_MEDIA.RENREN, SHARE_MEDIA.YNOTE
                };
                new ShareAction(getActivity())
                        .setDisplayList(list)//设置分享的平台列表
                        .withText(mEditShare.getText().toString())//分享的内容
                        .withTitle("分享的标题")//分享的标题
                        .withMedia(new UMImage(getActivity(), R.mipmap.ic_launcher))//多媒体:图片,音频,视频,表情等
                        //.withTargetUrl(url)//分享一个链接
                        //.addButton()
                        .setCallback(umShareListener).open();
                //分享的监听

                break;
            case R.id.live_start_btn:

                startActivity(new Intent(getActivity(), RecorderNoSkinActivity.class));
                break;

        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {


            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

}
