package com.lk.comecrazy.myself.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.lk.comecrazy.R;
import com.lk.comecrazy.attention.adapter.CollectAdapter;
import com.lk.comecrazy.base.BasePresenter;
import com.lk.comecrazy.home.Bean.CollectBean;
import com.lk.comecrazy.home.Video.VideoActivity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

//TODO:实现收藏功能,关注功能
public class AttentionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private Toolbar mBar;
    private BasePresenter mBasePresenter;
    private RelativeLayout relativeLayout;
    private ListView mLv;
    private RadioButton collect_delect;
    private DbManager db;
    private List<CollectBean> all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_attention);
        //初始化布局
        initView();
        SetData();
        setRelativeLayout();
        setDelect();
        //设置返回图标
        setIconBack();
    }

    private void SetData() {
        DbManager.DaoConfig config = new DbManager.DaoConfig();
        config.setDbName("collect.db");
        config.setDbVersion(1);
        db = x.getDb(config);
    }

    //删除全部方法
    private void setDelect() {
        collect_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.delete(CollectBean.class);
                    List<CollectBean> dbAll = db.findAll(CollectBean.class);
                    CollectAdapter adapter = new CollectAdapter();
                    adapter.setData(dbAll);
                    mLv.setAdapter(adapter);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //设置
    private void setRelativeLayout() {
        try {

            all = db.findAll(CollectBean.class);
            if (all != null) {
                mLv.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
                CollectAdapter adapter =  new CollectAdapter();
                adapter.setData(all);
                mLv.setAdapter(adapter);
            } else {
                mLv.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    //设置返回图标的设置
    private void setIconBack() {
        mBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出当前设置
                finish();
            }
        });
    }

    private void initView() {
        mBar = (Toolbar) findViewById(R.id.myself_attention_toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.attention_Collection);
        collect_delect = (RadioButton) findViewById(R.id.Collect_delect);
        mLv = (ListView) findViewById(R.id.lv);
        mLv.setVisibility(View.GONE);
        mLv.setOnItemLongClickListener(this);
        mLv.setOnItemClickListener(this);
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        CollectBean bean = all.get(position);
        String name = bean.getName();
        String city = bean.getCity();
        String logo = bean.getLogo();
        String video = bean.getVideo();
        Intent intent=new Intent(AttentionActivity.this, VideoActivity.class);
        intent.putExtra("video", video);
        intent.putExtra("name", name);
        intent.putExtra("city", city);
        intent.putExtra("logo", logo);
        startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        try {
            db.delete(all.get(position));
            all = db.findAll(CollectBean.class);
            CollectAdapter adapter =  new CollectAdapter();
            adapter.setData(all);
            mLv.setAdapter(adapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return true;
    }
}
