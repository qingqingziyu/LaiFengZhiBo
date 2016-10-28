package com.lk.comecrazy.home.view;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.HotBean;
import com.lk.comecrazy.home.adapter.ChanelAdapter;
import com.lk.comecrazy.home.model.HomeInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChanelFragment extends Fragment {
    private String hotUrl = HomeInfo.getHotUrl();
    ;
    private List<HotBean> mList = new ArrayList<>();
    private int HotPager = 1;

    private ListView mLv;

    public ChanelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chanel, container, false);
        initView(view);
        loadJson(hotUrl);
        return view;
    }

    //初始化
    private void initView(View view) {
        mLv = (ListView) view.findViewById(R.id.lv);

    }


    private Handler myHandler = new Handler();


    private void loadJson(String s) {
        new OkHttpClient().newCall(new Request.Builder().get().url(s).build()).enqueue(new Callback() {
            List<HotBean> list = new ArrayList<HotBean>();

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    HotBean hotBean = gson.fromJson(json, HotBean.class);
                    list.add(hotBean);
                }

                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mList.addAll(list);
                        ChanelAdapter adapter = new ChanelAdapter();
                        List<HotBean.ListBean> datas = list.get(0).getList();
                        Log.v("TAG", "频道总数据为" + datas);
                        adapter.setData(datas);
                        mLv.setAdapter(adapter);
                    }
                });

            }
        });

    }
}
