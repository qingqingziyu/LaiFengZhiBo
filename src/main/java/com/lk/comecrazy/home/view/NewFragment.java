package com.lk.comecrazy.home.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.HotBean;
import com.lk.comecrazy.home.Video.VideoActivity;
import com.lk.comecrazy.home.adapter.NewAdapter;
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
public class NewFragment extends Fragment implements AdapterView.OnItemClickListener {


    private PullToRefreshGridView new_grid;

    private List<HotBean.ListBean.LivelistBean> mList = new ArrayList<>();
    private int HotPager = 1;
    private String hotUrl = HomeInfo.getHotUrl();;

    Handler mHander=new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new, container, false);
        initView(view);

        initIndicator(); //设置上拉下拉刷新样式
        new_grid.setMode(PullToRefreshBase.Mode.BOTH); //设置模式
        LoadJson(hotUrl);
        /*上下那刷新方法*/
        new_grid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            //下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> pullToRefreshBase) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                new_grid.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                 /*下拉*/
                LoadJson(hotUrl);
            }

            //上拉
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> pullToRefreshBase) {
                  /*上拉*/
                LoadJson(hotUrl);
                HotPager++;
                if (HotPager > 3) {
                    HotPager = 1;
                }
            }
        });

        return view;
    }


    //初始化
    private void initView(View view) {
        new_grid = (PullToRefreshGridView) view.findViewById(R.id.new_gird);

        new_grid.setOnItemClickListener(this);
    }

    private void LoadJson(String path) {

        OkHttpClient okHttpClient=new OkHttpClient();
        okHttpClient.newCall(new Request.Builder().get().url(path).build()).enqueue(new Callback() {
            List<HotBean> list = new ArrayList<>();

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

                mHander.post(new Runnable() {

                    @Override
                    public void run() {
                        List<HotBean.ListBean.LivelistBean> data = null;
                        List<HotBean.ListBean.LivelistBean> datas = list.get(0).getList().get(2).getLivelist();
                        //加载多项数据
                        if (HotPager >= 1 && HotPager <= 3) {
                            data = list.get(0).getList().get(HotPager).getLivelist();
                        }
                        datas.addAll(data);
                        mList.addAll(datas);
                        NewAdapter adapter = new NewAdapter();
                        adapter.setData(datas,getActivity());
                        new_grid.setAdapter(adapter);
                        new_grid.onRefreshComplete();
                    }
                });
            }
        });
    }

    //设置上拉下拉刷新样式
    private void initIndicator() {
        //设置上部
        ILoadingLayout startLabels = new_grid.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示
        //设置下部
        ILoadingLayout endLabels = new_grid.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新...");// 刷新时
        endLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadJson(hotUrl);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        HotBean.ListBean.LivelistBean livelistBean = mList.get(position);
        String rtmp = livelistBean.getRtmp();
        String nickname = livelistBean.getUser().getNickname();
        String city = livelistBean.getUser().getCity();
        String avatar = livelistBean.getUser().getAvatar();
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("video", rtmp);
        intent.putExtra("name", nickname);
        intent.putExtra("city", city);
        intent.putExtra("logo", avatar);
        startActivity(intent);
    }
}
