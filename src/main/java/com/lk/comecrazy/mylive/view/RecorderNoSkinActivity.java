package com.lk.comecrazy.mylive.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.lk.comecrazy.R;

/**
 * 这个Activity 为了方便大家更快的了解和接入推流SDK。但是不建议大家直接使用这个
 * 在Activity中涉及CameraView类,这个也需要大家了解和使用。里面有很多提供的接口和配置,可以自行修改和设置
 * RecorderNoSkinActivity 和 CameraView 是不能满足大家的产品需求的,如果需要,可以自行改动或者复制相关代码
 */
public class RecorderNoSkinActivity extends Activity {
    private CameraView cameraView;
    private String path = "rtmp://216.mpush.live.lecloud.com/live/camerView";
    private CheckBox mOpen;
    private CheckBox mFlash;
    private CheckBox mFilter;
    private CheckBox mCamera;
    private CheckBox mVolume;

    //播放地址:rtmp://216.mpull.live.lecloud.com/live/camerView
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.live_layout);
        initView();

    }

    public void initView() {
        cameraView = (CameraView) findViewById(R.id.camera_view);
        mOpen = (CheckBox) findViewById(R.id.open);
        mOpen.setOnClickListener(listener);// 开始推流点击事件
        mFlash = (CheckBox) findViewById(R.id.change_flash);
        mFlash.setOnClickListener(listener);//闪光灯点击事件
        mFilter = (CheckBox) findViewById(R.id.switch_filter);
        mFilter.setOnClickListener(listener);//滤镜点击事件
        mCamera = (CheckBox) findViewById(R.id.switch_camera);
        mCamera.setOnClickListener(listener);//切换摄像头点击事件
        mVolume = (CheckBox) findViewById(R.id.set_volume);
        mVolume.setOnClickListener(listener);//声音点击事件
        cameraView.setTime((TextView) findViewById(R.id.time));
        cameraView.init(this, false);
        //设置美颜的图标的大小
        Drawable iconAttention = getResources().getDrawable(R.drawable.selector_meiyan);
        iconAttention.setBounds(0, 0, 55, 55);
        mFilter.setCompoundDrawables(null, iconAttention, null, null);
        //设置开始按钮的图标的大小
        Drawable iconStart = getResources().getDrawable(R.drawable.selector_live_start);
        iconStart.setBounds(0, 0, 100, 100);
        mOpen.setCompoundDrawables(null, iconStart, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.onDestroy();
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.open://开始推流以及结束推流
                    boolean isPublish = cameraView.publish(path);
                    if (isPublish) {
                        Toast.makeText(v.getContext(), "直播中", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(v.getContext(), "停止直播", Toast.LENGTH_SHORT).show();

                    }
                    break;
                case R.id.change_flash://切换闪光灯
                    boolean isCanFlash = cameraView.changeFlash();
                    if (!isCanFlash) {
                        mFlash.setChecked(false);
                    }
                    break;
                case R.id.switch_filter: //切换滤镜效果
                    cameraView.switchFilter();
                    break;
                case R.id.switch_camera://切换前后置摄像头
                    cameraView.switchCamera();
                    break;
                case R.id.set_volume://切换声音
                    cameraView.setVolume();
                    break;
            }
        }
    };
}
