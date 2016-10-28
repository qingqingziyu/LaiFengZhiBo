package com.lk.comecrazy.utils;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.lk.comecrazy.attention.view.AttentionFragment;
import com.lk.comecrazy.home.view.ChanelFragment;
import com.lk.comecrazy.home.view.HomeFragment;
import com.lk.comecrazy.home.view.HotFragment;
import com.lk.comecrazy.home.view.NewFragment;
import com.lk.comecrazy.message.view.MessageFragment;
import com.lk.comecrazy.mylive.view.LiveFragment;
import com.lk.comecrazy.myself.view.MyselfFragment;

/**
 * Created by Administrator on 2016/10/11.
 */
public class FragmentFactroy {
    private static final int FIST_FRAMENT = 0;
    private static final int SECOND_FRAMENT = 1;
    private static final int THRID_FRAMENT = 2;
    private static final int FOURTH_FRAMET = 3;
    private static final int FIFTH_FRAMET = 4;
    private static SparseArray<Fragment> fragments = new SparseArray<>();
    private static SparseArray<Fragment> HomeFragments = new SparseArray<>();

    public static Fragment createFragment(int index) {//创建一个Fragment的工厂

        Fragment fragment = fragments.get(index);
        if (fragment == null) {

            switch (index) {
                case FIST_FRAMENT:
                    fragment = new HomeFragment();
                    break;
                case SECOND_FRAMENT:
                    fragment = new AttentionFragment();
                    break;
                case THRID_FRAMENT:
                    fragment = new LiveFragment();
                    break;
                case FOURTH_FRAMET:
                    fragment = new MessageFragment();
                    break;
                case FIFTH_FRAMET:
                    fragment = new MyselfFragment();
                    break;
            }
            fragments.put(index, fragment);
        }
        return fragment;
    }

    //HomeFragmentFactroy
    public static Fragment createHomeFragment(int index){
        Fragment fragment = HomeFragments.get(index);
        if (fragment==null) {
            switch (index) {
                case 0:
                    fragment=new HotFragment();
                    break;
                case 1:
                    fragment=new NewFragment();
                    break;
                case 2:
                    fragment=new ChanelFragment();
                    break;
            }
            HomeFragments.put(index,fragment);
        }
        return fragment;
    }

}
