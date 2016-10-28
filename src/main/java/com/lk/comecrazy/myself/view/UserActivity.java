package com.lk.comecrazy.myself.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lk.comecrazy.R;
import com.lk.comecrazy.myself.presenter.UserPresenter;
import com.lk.comecrazy.utils.GlideRoundTransform;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.Unbinder;

//切换头像
public class UserActivity extends AppCompatActivity implements IUserInfo, View.OnClickListener {

    private ImageView mUserImageIcon;
    private TextView mUserTextName;
    private Toolbar mBackBar;
    private Unbinder mUnbinder;
    private int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;

    private static String picFileFullName;
    private UserPresenter mUserPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user);
        mUnbinder = ButterKnife.bind(this);

        //初始化布局
        initView();
        //设置图标返回
        setIconBack();
        mUserPresenter = new UserPresenter(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //保持图片
        mUserPresenter.keepIcon();
    }

    //保持头像
    @Override
    public void keepImageIcon(String name, String imageUrl) {
        if (name != null && imageUrl != null) {
            mUserTextName.setText(name);
            Log.i("MLOG", imageUrl);
            Glide.with(this).load(imageUrl).transform(new GlideRoundTransform(this)).into(mUserImageIcon);
        }
    }

    //设置图标返回
    private void setIconBack() {

        mBackBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //初始化布局
    private void initView() {
        mBackBar = (Toolbar) findViewById(R.id.user_toolbar);
        mUserImageIcon = (ImageView) findViewById(R.id.user_image_icon);
        mUserTextName = (TextView) findViewById(R.id.user_text_name);
        mUserImageIcon.setOnClickListener(this);
        mUserTextName.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image_icon://设置头像图标的点击事件

                mUserPresenter.showWindow(view);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            //解除绑定黄油刀
            mUnbinder.unbind();
        }
    }

    //拍照
    @Override
    public void takePicture() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        } else {

        }
    }

    //打开相册
    @Override
    public void openAlbum() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    //设置头像
    @Override
    public void setIcon(String path) {
        int degree = mUserPresenter.readPictureDegree(path);

        //根据不同角度设置拍照结果设置在头像位置
        if (degree <= 0) {
            //设置头像
            Glide.with(this).load(path).transform(new GlideRoundTransform(this)).into(mUserImageIcon);


        } else {
            //设置头像
            Glide.with(this).load(path).transform(new GlideRoundTransform(this)).into(mUserImageIcon);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {  // //获取拍照后的回掉结果
            if (resultCode == RESULT_OK) {
                if (picFileFullName != null) {
                    //把拍照头像保存到本地数据库中
                    mUserPresenter.saveSqlite(mUserTextName.getText().toString(), picFileFullName);
                    //设置头像
                    mUserPresenter.setImageIcon(picFileFullName);
                }

            } else if (resultCode == RESULT_CANCELED) {
                // 用户取消了图像捕获
            } else {
                // 图像捕获失败，提示用户
            }
        } else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {  //获取打开相册回掉的图片
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    //得到图片的路径
                    String realPath = getRealPathFromURI(uri);
                    //把图片和名称放到数据库中
                    mUserPresenter.saveSqlite(mUserTextName.getText().toString(), realPath);
                    //TODO:创建一个工作线程实现上传图片
                    //  workThread(realPath);
                    //设置本地相册的图片设为头像
                    mUserPresenter.setImageIcon(realPath);
                } else {
                    Toast.makeText(UserActivity.this, "网络异常，图片上传失败", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    //开启弹窗
    @Override
    public void showPopupWindow(View view) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.icon_popwindow, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        //设置弹窗的项目点击事件
        mUserPresenter.setWindowItem(contentView);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                android.R.drawable.editbox_background));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);
    }

    //设置弹窗子菜单的点击事件
    @Override
    public void setPopupMenuItem(View view) {
        Button photoBut = (Button) view.findViewById(R.id.take_photo);
        Button ablumBut = (Button) view.findViewById(R.id.local_ablum);
        //设置拍照片，打开相机
        photoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //照相
                mUserPresenter.doTakePohto();
            }
        });
        //设置获取本地相册
        ablumBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开本地相册
                mUserPresenter.openMyAlbum();
            }
        });
    }

    //获取拍照的路径
    @Override
    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }


    //上传图片到服务器
    @Override
    public void postIcon(String path) {

    }
}
