package com.lk.comecrazy.myself.presenter;


import android.content.Context;
import android.media.ExifInterface;
import android.view.View;

import com.lk.comecrazy.myself.model.UserDbModel;
import com.lk.comecrazy.myself.view.IUserInfo;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/20.
 */
public class UserPresenter {

    private UserDbModel mDbModel;

    private IUserInfo mView;

    //获取Activity的对象
    public UserPresenter(IUserInfo view) {
        mDbModel = new UserDbModel((Context) view);

        this.mView = view;
    }

    //把照片和名称放到数据库里
    public void saveSqlite(String name, String imageUrl) {
        //放置到数据库中
        mDbModel.insert(name, imageUrl);
    }

    //拍照片
    public void doTakePohto() {
        mView.takePicture();
    }

    //打开相册
    public void openMyAlbum() {
        mView.openAlbum();
    }


    //弹出窗口
    public void showWindow(View view) {
        mView.showPopupWindow(view);
    }

    //设置头像
    public void setImageIcon(String path) {
        mView.setIcon(path);
    }
    //获取拍照的角度

    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    //popWindow的子条目事件监听
    public void setWindowItem(View view) {
        mView.setPopupMenuItem(view);

    }

    //保持头像
    public void keepIcon() {


        Map<String, String> map = mDbModel.read();
        String name = map.get("name");
        String url = map.get("url");

        mView.keepImageIcon(name, url);
    }
}
