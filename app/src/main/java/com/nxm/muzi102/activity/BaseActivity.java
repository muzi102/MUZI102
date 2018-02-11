package com.nxm.muzi102.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.githang.statusbar.StatusBarCompat;
import com.nxm.muzi102.R;
import com.nxm.muzi102.application.MyApplication;
import com.nxm.muzi102.comment.AppConstant;
import com.nxm.muzi102.listener.ActivityInitListener;

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月1日20:13:55          lzx              Activity 基类
 * *******************************************************************************************
 */
public abstract class BaseActivity extends FragmentActivity implements ActivityInitListener {
    //受保护的对象
    protected Intent mIntent;
    private String key = null;

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        //视屏
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        //不随系统字体变化
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        mIntent = new Intent();
        try {
            setContentView(getContentView());
            onViewsDidLoad(savedInstanceState);
        } catch (Exception e) {
            e.getStackTrace();
        }
        //添加到Activity管理
        String name = this.toString();
        key = name.substring(0, name.indexOf(AppConstant.AT));
        MyApplication.map.put(key, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.map.remove(key);
    }

    //跳转到登录界面
    protected void goToLoginActivity() {
        LoginActivity.Companion.actionStart(this);
    }
}
