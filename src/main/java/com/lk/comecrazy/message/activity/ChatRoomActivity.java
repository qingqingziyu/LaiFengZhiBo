package com.lk.comecrazy.message.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lk.comecrazy.R;
import com.lk.comecrazy.message.adapter.ChatAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mLv;
    private Toolbar toolbar;
    private EditText et_text;
    private Button btn_chat;
    private List<String> mList;
    private String mtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认标题栏
        setContentView(R.layout.activity_chat_room);
        initView();
        setToobar();
    }

    //设置适配器
    private void setAdapter() {

    }

    //设置Toobar
    private void setToobar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //初始化
    private void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        toolbar = (Toolbar) findViewById(R.id.chat_toobar);
        et_text = (EditText) findViewById(R.id.et_chat);
        btn_chat = (Button) findViewById(R.id.btn_chat);
        btn_chat.setOnClickListener(this);
        mList = new ArrayList<>();
        mtext = et_text.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date data=new Date(System.currentTimeMillis());
        ChatAdapter adapter=new ChatAdapter();
        String time = format.format(data);
        adapter.setData(mList,time,"as");
        mLv.setAdapter(adapter);
    }

    //设置返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_chat:
                //得到数据源
                Toast.makeText(this, "已发送成功", Toast.LENGTH_SHORT).show();
                    String text = et_text.getText().toString();
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                    Date data=new Date(System.currentTimeMillis());
                    ChatAdapter adapter=new ChatAdapter();
                    String time = format.format(data);
                    mList.add(text);
                    adapter.setData(mList,time,text);
                    mLv.setAdapter(adapter);
                    et_text.setText("");
                break;
        }
    }
}
