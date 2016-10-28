package com.lk.comecrazy.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lk.comecrazy.R;
import com.lk.comecrazy.message.bean.ZiXunBean;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/21 0021 21:47 .
 * 备注：
 */

public class ZiXunAdapter extends BaseAdapter {
    List<ZiXunBean> mList;

    public void setData(List<ZiXunBean> list) {
        this.mList = list;
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.zixun_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        ZiXunBean bean = mList.get(position);
        String content = bean.getContent();
        int logo = bean.getLogo();
        holder.content.setText(content);
        holder.logo.setImageResource(logo);
        return convertView;
    }
    class ViewHolder{
        ImageView logo;
        TextView content;
        ViewHolder(View convertView){
            logo = (ImageView) convertView.findViewById(R.id.zixu_logo);
            content = (TextView) convertView.findViewById(R.id.zixun_content);
        }
    }
}
