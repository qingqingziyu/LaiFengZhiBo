package com.lk.comecrazy.home.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * 类描述：
 * 类名称：
 * 作者：飞哥
 * 创建时间:2016/10/24 0024 11:49 .
 * 备注：
 */

public class GlideImageLoador implements ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
