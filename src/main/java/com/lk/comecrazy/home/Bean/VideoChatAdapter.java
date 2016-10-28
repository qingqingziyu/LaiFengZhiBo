package com.lk.comecrazy.home.Bean;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lk.comecrazy.R;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/22 0022 20:11 .
 * 备注：
 */

public class VideoChatAdapter extends BaseAdapter {
    List<String> mList;

    public void setData(List<String> list) {
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.video_chat_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        String s = mList.get(position);
        holder.content.setText(s);
        return convertView;
    }
    class ViewHolder{
        TextView content;
        ViewHolder(View convertView){
            content = (TextView) convertView.findViewById(R.id.chat_ed_video);
        }
    }
}
