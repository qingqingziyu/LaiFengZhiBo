package com.lk.comecrazy.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.lk.comecrazy.R;
import com.lk.comecrazy.home.Bean.HotBean;

import java.util.List;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/18 0018 21:39 .
 * 备注：
 */
public class HotAdapter extends BaseAdapter {
    List<HotBean.ListBean.LivelistBean> mList;

    public void setData(List<HotBean.ListBean.LivelistBean> list) {
        this.mList =list ;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_list,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        String cover = mList.get(position).getCover();
        String avatar = mList.get(position).getUser().getAvatar();
        String nickname = mList.get(position).getUser().getNickname();
        String city = mList.get(position).getUser().getCity();
        int users = mList.get(position).getUsers();
        //Glide下载图
        Glide.with(parent.getContext()).load(cover).into(holder.cover_image);
        Glide.with(parent.getContext()).load(avatar).transform(new CircleTransform(parent.getContext())).into(holder.avatar_image);
        holder.nickname_tv.setText(nickname);
        holder.city_tv.setText(city);
        holder.users_tv.setText("赞:"+users);
        return convertView;
    }

    class ViewHolder {
        ImageView avatar_image, cover_image;
        TextView nickname_tv, city_tv, users_tv;

        ViewHolder(View convertView) {
            avatar_image = (ImageView) convertView.findViewById(R.id.hot_avatar_image); //作者图片
            cover_image = (ImageView) convertView.findViewById(R.id.Hot_cover_image);
            nickname_tv = (TextView) convertView.findViewById(R.id.hot_nickname_tv);
            city_tv = (TextView) convertView.findViewById(R.id.Hot_city_tv);
            users_tv = (TextView) convertView.findViewById(R.id.Hot_users_tv);
        }
    }
    //Glide插件圆形图片类
    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }

}
