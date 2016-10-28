package com.lk.comecrazy.home.model;

import android.support.v4.app.Fragment;

import com.lk.comecrazy.utils.FragmentFactroy;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/16 0016 6:56 .
 * 备注：
 */

public class HomeInfo {
    //tab标题数据
    public static String[] getTitle() {
        String[] title = new String[]{"热门", "最新", "频道"};
        return title;
    }
    //Fragment数据
    public static List<Fragment> getFragmentData() {
        List<Fragment> mList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Fragment fragment = FragmentFactroy.createHomeFragment(i);
            mList.add(fragment);
        }
        return mList;
    }

    public static String getHotUrl(){
        String Home_hot="http://api.yiqi1717.com/home/get_multi?nonce=wfeyk7g3tz&pageindex=1&publickey=3.9&tag_id=1&timestamp=1476792143&token=c87ed51849b51c3db4bfad026c33c201&signature=f0b0c3f3d732789b502711f769562c8d\n";
        return Home_hot;
    }
}
