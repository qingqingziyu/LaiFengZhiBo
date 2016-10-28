package com.lk.comecrazy.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.HotBean;
import com.lk.comecrazy.home.GridViewLayout.NoScrollGridView;
import com.lk.comecrazy.home.Video.VideoActivity;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/21 0021 10:11 .
 * 备注：
 */

public class ChanelAdapter extends BaseAdapter implements AdapterView.OnItemClickListener{

    private List<HotBean.ListBean> mList;
    private List<HotBean.ListBean.LivelistBean> been;
    private Context mContent;

    public void setData(List<HotBean.ListBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return mList != null ? mList.get(i) : null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        this.mContent=parent.getContext();
        String name = mList.get(position).getName();
        Log.d("TAG", "频道标题为" + name);
        been = mList.get(position).getLivelist();
        holder.title.setText(name);
        GridViewAdapter adapter = new GridViewAdapter();
        adapter.setData(been);
        holder.gridView.setAdapter(adapter);
        holder.gridView.setOnItemClickListener(this);
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        HotBean.ListBean.LivelistBean livelistBean = been.get(position);
        String rtmp = livelistBean.getRtmp();
        String nickname = livelistBean.getUser().getNickname();
        String city = livelistBean.getUser().getCity();
        String avatar = livelistBean.getUser().getAvatar();
        Intent intent = new Intent(mContent, VideoActivity.class);
        Log.d("TAG", "视频地址为：" + rtmp);
        intent.putExtra("video", rtmp);
        intent.putExtra("name", nickname);
        intent.putExtra("city", city);
        intent.putExtra("logo", avatar);
        mContent.startActivity(intent);
    }

    class ViewHolder {
        TextView title;
        NoScrollGridView gridView;

        ViewHolder(View convertView) {
            title = (TextView) convertView.findViewById(R.id.channel_title);
            gridView = (NoScrollGridView) convertView.findViewById(R.id.channel_grid);
        }
    }

}
