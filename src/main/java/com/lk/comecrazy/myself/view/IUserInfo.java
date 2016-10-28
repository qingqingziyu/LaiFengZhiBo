package com.lk.comecrazy.myself.view;

import android.net.Uri;
import android.view.View;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface IUserInfo {
    void takePicture();//拍照片

    void openAlbum();//打开本地相册

    void setIcon(String path); //设置头像

    void showPopupWindow(View view);//设置弹窗

    void setPopupMenuItem(View view); //设置弹窗的子菜单点击事件

    String getRealPathFromURI(Uri contentUri); //获取拍照的地址

    void keepImageIcon(String name, String url); //保持照片状态

    void postIcon(String path); //上传图片到云端
}
