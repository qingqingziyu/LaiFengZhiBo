package com.lk.comecrazy.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lk.comecrazy.R;
import com.lk.comecrazy.message.bean.MessageBean;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/21 0021 16:57 .
 * 备注：
 */

public class MessageAdapter extends BaseAdapter {
    private List<MessageBean> mList;

    //设置数据源
    public void setData(List<MessageBean> list) {
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        MessageBean bean = mList.get(position);
        int logo = bean.getLogo();
        String content = bean.getContent();
        String title = bean.getTitle();
        holder.logo.setImageResource(logo);
        holder.title.setText(title);
        holder.content.setText(content);
        return convertView;
    }
    class ViewHolder{
        ImageView logo;
        TextView title,content;
        ViewHolder(View view){
            logo = (ImageView) view.findViewById(R.id.message_logo);
            title = (TextView) view.findViewById(R.id.message_title);
            content = (TextView) view.findViewById(R.id.message_content);
        }
    }
}
