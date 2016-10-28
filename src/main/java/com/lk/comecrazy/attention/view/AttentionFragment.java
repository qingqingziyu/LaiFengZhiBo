package com.lk.comecrazy.attention.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.lk.comecrazy.R;
import com.lk.comecrazy.attention.adapter.CollectAdapter;
import com.lk.comecrazy.base.BasePresenter;
import com.lk.comecrazy.base.MainActivity;
import com.lk.comecrazy.home.Bean.CollectBean;
import com.lk.comecrazy.home.Video.VideoActivity;
import com.lk.comecrazy.utils.FragmentFactroy;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * <p/>
 * 关注的界面Fragement
 */
public class AttentionFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener, AdapterView.OnItemLongClickListener {


    private BasePresenter mBasePresenter;
    private RelativeLayout relativeLayout;
    private ListView mLv;
    private RadioButton collect_delect;
    private DbManager db;
    private List<CollectBean> all;

    public AttentionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attention, container, false);
        initView(view);
        SetData();
        mBasePresenter = new BasePresenter((MainActivity) getActivity());
        setRelativeLayout();
        setDelect();
        return view;

    }

    private void SetData() {
        DbManager.DaoConfig config = new DbManager.DaoConfig();
        config.setDbName("collect.db");
        config.setDbVersion(1);
        db = x.getDb(config);
    }

    //删除全部方法
    private void setDelect() {
        collect_delect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.delete(CollectBean.class);
                    List<CollectBean> dbAll = db.findAll(CollectBean.class);
                    CollectAdapter adapter = new CollectAdapter();
                    adapter.setData(dbAll);
                    mLv.setAdapter(adapter);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //设置
    private void setRelativeLayout() {
        try {

            all = db.findAll(CollectBean.class);
            if (all != null) {
                mLv.setVisibility(View.VISIBLE);
                relativeLayout.setVisibility(View.GONE);
                CollectAdapter adapter =  new CollectAdapter();
                adapter.setData(all);
                mLv.setAdapter(adapter);
            } else {
                mLv.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void initView(View view) {
        ((Button) view.findViewById(R.id.btn_atttion)).setOnClickListener(this);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.attention_Collection);
        collect_delect = (RadioButton) view.findViewById(R.id.Collect_delect);
        mLv = (ListView) view.findViewById(R.id.lv);
        mLv.setVisibility(View.GONE);
        mLv.setOnItemLongClickListener(this);
        mLv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_atttion:
                //返回首页
                mBasePresenter.backHome();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame_layout, FragmentFactroy.createFragment(0));
                fragmentTransaction.commit();
                break;
        }
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        try {
            db.delete(all.get(position));
            all = db.findAll(CollectBean.class);
            CollectAdapter adapter =  new CollectAdapter();
            adapter.setData(all);
            mLv.setAdapter(adapter);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        CollectBean bean = all.get(position);
        String name = bean.getName();
        String city = bean.getCity();
        String logo = bean.getLogo();
        String video = bean.getVideo();
        Intent intent=new Intent(getActivity(), VideoActivity.class);
        intent.putExtra("video", video);
        intent.putExtra("name", name);
        intent.putExtra("city", city);
        intent.putExtra("logo", logo);
        startActivity(intent);
    }
}
