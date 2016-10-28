package com.lk.comecrazy.home.Video;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.CollectBean;
import com.lk.comecrazy.home.Bean.VideoChatAdapter;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class VideoActivity extends Activity implements View.OnClickListener {

    private ImageButton btn_ed;
    private ImageButton collection;
    private EditText et_input;
    private Button sendMessage;
    private String name;
    private String city;
    private String path;

    private String logo;
    private ListView mLv;
    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = VideoActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        path = intent.getStringExtra("video");
        name = intent.getStringExtra("name");
        city = intent.getStringExtra("city");
        logo = intent.getStringExtra("logo");

        if (Vitamio.isInitialized(this)) {
            VideoView videoView = (VideoView) findViewById(R.id.video_paly);
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();
        }
        mData = new ArrayList<>();
        initView();
    }



    //初始化
    private void initView() {
        btn_ed = (ImageButton) findViewById(R.id.btn_dt);
        collection = (ImageButton) findViewById(R.id.collection);
        et_input = (EditText) findViewById(R.id.et_input);
        sendMessage = (Button) findViewById(R.id.send_message);
        btn_ed.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
        sendMessage.setVisibility(View.INVISIBLE);
        /*et_input.setOnClickListener(this);*/
        et_input.setVisibility(View.INVISIBLE);
        mLv = (ListView) findViewById(R.id.lv);

        //收藏按钮事件
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = true;
                DbManager.DaoConfig config = new DbManager.DaoConfig();
                config.setDbName("collect.db");
                config.setDbVersion(1);
                DbManager db = x.getDb(config);
                //存储收藏数据
                try {
                    List<CollectBean> all = db.findAll(CollectBean.class);
                    if (all == null) {
                        CollectBean bean = new CollectBean();
                        bean.setName(name);
                        bean.setCity(city);
                        bean.setVideo(path);
                        bean.setLogo(logo);
                        db.save(bean);
                    } else {
                        for (CollectBean bean : all) {
                            if (bean.getName().equals(name)) {
                                isCheck = false;
                                Toast.makeText(VideoActivity.this, "你收藏过了，请不要重复收藏!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (isCheck) {
                            CollectBean bean = new CollectBean();
                            bean.setName(name);
                            bean.setCity(city);
                            bean.setVideo(path);
                            bean.setLogo(logo);
                            db.save(bean);
                        }
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }

                TSnackbar snackbar = TSnackbar.make(view, "关注成功", TSnackbar.LENGTH_LONG).setActionTextColor(Color.BLACK);
                snackbar.getView().setBackgroundColor(Color.YELLOW);
                snackbar.show();
            }
        });
    }

    //返回键事件
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //点击事件
    @Override
    public void onClick(View view) {
        int visibility = et_input.getVisibility();
        switch (view.getId()) {
            case R.id.btn_dt:  //显示编辑框事件
                if (visibility == 0) {
                    et_input.setVisibility(View.INVISIBLE);
                    sendMessage.setVisibility(View.INVISIBLE);
                    collection.setVisibility(View.VISIBLE);
                } else {
                    et_input.setVisibility(View.VISIBLE);
                    sendMessage.setVisibility(View.VISIBLE);
                    collection.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.send_message:
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
                String content = et_input.getText().toString();

                mData.add(content);
//                ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mData);
                VideoChatAdapter adapter=new VideoChatAdapter();
                adapter.setData(mData);
                mLv.setAdapter(adapter);
                et_input.setText("");
                break;

        }
    }
}
