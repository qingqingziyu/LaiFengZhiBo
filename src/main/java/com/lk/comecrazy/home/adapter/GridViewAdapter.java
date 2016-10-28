package com.lk.comecrazy.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.HotBean;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/21 0021 10:43 .
 * 备注：
 */

public class GridViewAdapter extends BaseAdapter {
    private List<HotBean.ListBean.LivelistBean> mList;

    public void setData(List<HotBean.ListBean.LivelistBean> list) {
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
        if (convertView==null) {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_list,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        //绑定数据
        //绑定数据
        String cover = mList.get(position).getCover();
        String avatar = mList.get(position).getUser().getAvatar();
        String nickname = mList.get(position).getUser().getNickname();
        int users = mList.get(position).getUsers();
        Glide.with(parent.getContext()).load(cover).into(holder.new_girlImage);
        holder.new_girlName.setText(nickname);
        holder.new_girlUsers.setText("赞"+users);
        return convertView;
    }
    class ViewHolder{
        ImageView new_girlImage;
        TextView new_girlName,new_girlUsers;
        ViewHolder(View convertView){
            new_girlImage=(ImageView) convertView.findViewById(R.id.new_girlImage);
            new_girlName = (TextView) convertView.findViewById(R.id.new_girlName);
            new_girlUsers = (TextView) convertView.findViewById(R.id.new_users);
        }
    }
}
