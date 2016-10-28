package com.lk.comecrazy.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/16 0016 15:21 .
 * 备注：
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;
    String[] title;
    public HomeViewPagerAdapter(FragmentManager fm, List<Fragment> list, String[] titles) {
        super(fm);
        this.mList=list;
        this.title=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

}
