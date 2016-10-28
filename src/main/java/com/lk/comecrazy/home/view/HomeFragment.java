package com.lk.comecrazy.home.view;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lk.comecrazy.R;
import com.lk.comecrazy.home.adapter.HomeViewPagerAdapter;
import com.lk.comecrazy.utils.FragmentFactroy;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * 首页Fragment
 */
public class HomeFragment extends Fragment{


    private Toolbar homeToobar;
    private TabLayout homeTab;
    private ViewPager homeVp;
    private String[] title;
    private List<Fragment> mList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        initTab();
        initCodeStart();

        return view;
    }


    //初始化
    private void initView(View view) {
        homeToobar = (Toolbar) view.findViewById(R.id.home_toobar);
        homeTab = (TabLayout) view.findViewById(R.id.home_tab);
        homeVp = (ViewPager) view.findViewById(R.id.vp);
    }

    //代码开始
    private void initCodeStart() {


    }

    //数据源
    private void initData() {
       /* HomeInfo info=new HomeInfo();
        title = info.getTitle();
        mList = info.getFragmentData();*/
         title = getTitle();
        getFragmentData();
    }

    //tab标题数据
    public String[] getTitle() {
        String[] title = new String[]{"热门", "最新", "频道"};
        return title;
    }

    //Fragment数据
    public void getFragmentData() {
        mList = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            //创建fragment
            Fragment fragment = FragmentFactroy.createHomeFragment(i);
            mList.add(fragment);
            initViewPagerAdapter();
        }
    }
    //导航条初始化
    private void initTab() {
        homeTab.setupWithViewPager(homeVp, true);/*tab和viewpager关联*/
        homeTab.setSelectedTabIndicatorColor(Color.YELLOW);/*设置选中导航条的颜色*/
        homeTab.setSelectedTabIndicatorHeight(5);/*指示器高度*/
        homeTab.setTabTextColors(Color.GRAY, Color.BLACK);/*选中与被选中字体的颜色*/
        homeTab.setTabMode(TabLayout.MODE_FIXED);
    }

    //ViewPagwer适配器
    private void initViewPagerAdapter() {
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getChildFragmentManager(),mList,title);
        homeVp.setAdapter(adapter);
//        homeVp.setOffscreenPageLimit(3);
        homeVp.setCurrentItem(0,false);


    }

    @Override
    public void onResume() {
        super.onResume();
//        initViewPagerAdapter();
    }
}
