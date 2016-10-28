package com.lk.comecrazy.home.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lk.comecrazy.R;

public class WebActivity extends Activity {

    private WebView web;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        Intent intent = getIntent();
        String path = intent.getStringExtra("web");
        WebSettings settings = web.getSettings();
        settings.setDisplayZoomControls(true);
        settings.setJavaScriptEnabled(true);
        web.loadUrl(path);
        setToobarListenner();
    }
    //toobar事件
    private void setToobarListenner() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //初始化
    private void initView() {
        web = (WebView) findViewById(R.id.web);
        toolbar = (Toolbar) findViewById(R.id.web_toobar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
