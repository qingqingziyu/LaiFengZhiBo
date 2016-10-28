package com.lk.comecrazy.message.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lk.comecrazy.R;

import java.util.List;

import static com.lk.comecrazy.R.layout.chat_item_master;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/22 0022 0:37 .
 * 备注：
 */

public class ChatAdapter extends BaseAdapter {
    private List<String> mList;
    private String chatRoomTime;
    private String mtext;

    public void setData(List<String> list,String time,String text) {
        this.mList = list;
        this.chatRoomTime=time;
        this.mtext=text;
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
        int chat_item;
        //判断聊天室
        if (TextUtils.isEmpty(mtext)) {
            if (convertView==null) {
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);

            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.time.setText(chatRoomTime);
        }else {
            if (convertView==null) {
                convertView= LayoutInflater.from(parent.getContext()).inflate(chat_item_master,parent,false);
                holder=new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            //绑定数据
            String s = mList.get(position);
            holder.time.setText(chatRoomTime);
            holder.content.setText(s);
        }

        return convertView;
    }
    class ViewHolder{
        TextView content,time;
        ViewHolder(View convertView){
            content = (TextView) convertView.findViewById(R.id.chat_content);
            time = (TextView) convertView.findViewById(R.id.chat_time);
        }
    }
}
