package com.lk.comecrazy.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lk.comecrazy.R;
import com.lk.comecrazy.message.adapter.ZiXunAdapter;
import com.lk.comecrazy.message.bean.ZiXunBean;

import java.util.ArrayList;
import java.util.List;

public class ZiXunActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolBar;
    private ListView mLv;
    private List<ZiXunBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zi_xun);
        initView();
        setToolBar();
        getData();
        setAdapter();
    }

    //设置适配器
    private void setAdapter() {
        ZiXunAdapter adapter = new ZiXunAdapter();
        adapter.setData(mList);
        mLv.setAdapter(adapter);
    }

    //设置导航栏
    private void setToolBar() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //初始化
    private void initView() {
        toolBar = (Toolbar) findViewById(R.id.ZiXun_toobar);
        mLv = (ListView) findViewById(R.id.lv);
        mLv.setOnItemClickListener(this);
    }


    /***
     * 数据源
     */
    //内容
    private String[] getContent() {
        String[] content = new String[]{"直播问题", "账号问题", "充值咨询", "家族问题", "投诉/建议", "其他问题"};
        return content;
    }

    //logo数据
    private int[] getLogo() {
        int[] logo = new int[]{R.mipmap.kefu_live, R.mipmap.kefu_zhanghao, R.mipmap.kefu_chongzhi, R.mipmap.kefu_jiazu, R.mipmap.kefu_tousujianyi, R.mipmap.kefu_qitawenti};
        return logo;
    }

    //按返回键事件
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //得到数据
    public void getData() {
        mList = new ArrayList<>();
        int[] logo = getLogo();
        String[] content = getContent();
        for (int i = 0; i < logo.length; i++) {
            ZiXunBean bean = new ZiXunBean(content[i], logo[i]);
            mList.add(bean);
        }
    }

    //点击条目事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        startActivity(new Intent(ZiXunActivity.this,ChatRoomActivity.class));
    }
}
