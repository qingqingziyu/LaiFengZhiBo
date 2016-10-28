package com.lk.comecrazy.attention.adapter;

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
import com.lk.comecrazy.home.Bean.CollectBean;

import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/22 0022 16:53 .
 * 备注：
 */

public class CollectAdapter extends BaseAdapter {
    List<CollectBean> mList;

    public void setData(List<CollectBean> list) {
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
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.collect_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //绑定数据
        CollectBean bean = mList.get(position);
        String logo = bean.getLogo();
        String name = bean.getName();
        String city = bean.getCity();
        Glide.with(parent.getContext()).load(logo).transform(new CircleTransform(context)).into(holder.logo);
        holder.name.setText(name);
        holder.city.setText(city);
        return convertView;
    }
    class ViewHolder{
        ImageView logo;
        TextView name,city;
        ViewHolder(View convertView){
            logo=(ImageView) convertView.findViewById(R.id.Collect_logo);
            name=(TextView) convertView.findViewById(R.id.Collect_name);
            city=(TextView) convertView.findViewById(R.id.Collect_city);
        }
    }

    //Glide图片圆形类：
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
