package com.lk.comecrazy.message.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lk.comecrazy.R;
import com.lk.comecrazy.message.activity.MessageActivity;
import com.lk.comecrazy.message.activity.ZiXunActivity;
import com.lk.comecrazy.message.adapter.MessageAdapter;
import com.lk.comecrazy.message.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass
 * <p>
 * 消息Fragment.
 */
public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {


    private ListView mLv;
    private List<MessageBean> mList;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView(view);
        getData();
        setAdapter();
        return view;
    }


    //初始化
    private void initView(View view) {
        mLv = (ListView) view.findViewById(R.id.lv);
        mLv.setOnItemClickListener(this);
    }

    //得到数据集合
    public void getData() {
        mList = new ArrayList<>();
        String[] title = getTitle();
        String[] content = getContent();
        int[] logo = getLogo();
        for (int i = 0; i < title.length; i++) {
            MessageBean messageBean = new MessageBean(title[i], content[i], logo[i]);
            mList.add(messageBean);
        }
    }

    //设置适配器
    private void setAdapter() {
        MessageAdapter adapter=new MessageAdapter();
        adapter.setData(mList);
        mLv.setAdapter(adapter);
    }


    /**
     * 数据源
     */
    //标题数据
    private String[] getTitle() {
        String[] title = new String[]{"来疯客服", "官方通知"};
        return title;
    }

    //内容数据
    private String[] getContent() {
        String[] content = new String[]{"宝宝请稍等，将帮你转接人工客服处理~", "仅限两天，星座大奖来袭。全新天秤の守护上线，送主播全新星座礼物，还能获得十万花椒币和热门排名，快来参与"};
        return content;
    }

    //图片
    private int[] getLogo() {
        int[] logo = new int[]{R.mipmap.ic_message_service, R.mipmap.logo180};
        return logo;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position==0) {
            startActivity(new Intent(getActivity(), ZiXunActivity.class));
        }else {
            startActivity(new Intent(getActivity(), MessageActivity.class));
        }
    }
}
