package com.lk.comecrazy.home.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.HotBean;
import com.lk.comecrazy.home.Video.VideoActivity;
import com.lk.comecrazy.home.activity.WebActivity;
import com.lk.comecrazy.home.adapter.HotAdapter;
import com.lk.comecrazy.home.model.HomeInfo;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

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
public class HotFragment extends Fragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView mLv;
    private String hotUrl = HomeInfo.getHotUrl();
    ;
    private List<HotBean.ListBean.LivelistBean> mList = new ArrayList<>();
    private int HotPager = 1;

    private Banner hotHead;


    public HotFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        initView(view);
        initIndicator(); //上拉刷新下拉加载样式：
        loadJson(hotUrl);
        initSetLisView();
        setListViewListener();//设置监听事件
        return view;
    }


    private void initView(View view) {
        mLv = (PullToRefreshListView) view.findViewById(R.id.lv);
        mLv.setOnItemClickListener(this);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.head_list, null);
        hotHead = (Banner) headView.findViewById(R.id.Hot_Head);
        mLv.addHeaderView(headView);
    }

    //设置listView
    private void initSetLisView() {
        mLv.setMode(PullToRefreshBase.Mode.BOTH); //设置上拉加载，下拉刷新模式
    }

    //设置listView监听事件
    private void setListViewListener() {
        //刷新
        mLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                mLv.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                /*下拉*/
                loadJson(hotUrl);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                /*上拉*/
                loadJson(hotUrl);
                HotPager++;
                if (HotPager > 3) {
                    HotPager = 1;
                }
            }
        });

    }


    //上拉刷新下拉加载样式：
    private void initIndicator() {
        //设置上部
        ILoadingLayout startLabels = mLv.getLoadingLayoutProxy(true, false);
        startLabels.setPullLabel("下拉刷新...");// 刚下拉时，显示的提示
        startLabels.setRefreshingLabel("正在刷新...");// 刷新时
        startLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示
        //设置下部
        ILoadingLayout endLabels = mLv.getLoadingLayoutProxy(false, true);
        endLabels.setPullLabel("上拉刷新...");// 刚下拉时，显示的提示
        endLabels.setRefreshingLabel("正在刷新...");// 刷新时
        endLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示
    }

    Handler mHander = new Handler();

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
                mHander.post(new Runnable() {
                    private List<HotBean.ListBean.LivelistBean> data;

                    @Override
                    public void run() {
                        setHead(list);
                        HotAdapter adapter = new HotAdapter();
                        List<HotBean.ListBean.LivelistBean> datas = list.get(0).getList().get(1).getLivelist();
                        //加载多项数据
                        if (HotPager >= 1 && HotPager <= 3) {
                            data = list.get(0).getList().get(HotPager).getLivelist();
                        }

                        datas.addAll(data);
                        mList.addAll(datas);

                        adapter.setData(datas);
                        mLv.setAdapter(adapter);
                        mLv.onRefreshComplete();
                    }
                });
            }
        });

    }


    //实现广告轮播
    private void setHead(List<HotBean> HeadList) {

        List<String> images=new ArrayList<>();
        final List<String> webUrl=new ArrayList<>();
        for (int i = 0; i < HeadList.get(0).getBar().size(); i++) {
            images.add(HeadList.get(0).getBar().get(i).getImage());
            webUrl.add(HeadList.get(0).getBar().get(i).getParameters());
        }
        hotHead.setImageLoader(new GlideImageLoador()).setImages(images).start();

        hotHead.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                String data = webUrl.get(position-1);
                String substring = data.substring(5);
                Intent intent=new Intent(getActivity(), WebActivity.class);
                intent.putExtra("web",substring);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        HotBean.ListBean.LivelistBean livelistBean = mList.get(position-1);
        String rtmp = livelistBean.getRtmp();
        String nickname = livelistBean.getUser().getNickname();
        String city = livelistBean.getUser().getCity();
        String avatar = livelistBean.getUser().getAvatar();
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        Log.d("TAG", "视频地址为：" + rtmp);
        intent.putExtra("video", rtmp);
        intent.putExtra("name", nickname);
        intent.putExtra("city", city);
        intent.putExtra("logo", avatar);
        startActivity(intent);
    }
}
