package com.nxm.muzi102.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.nxm.muzi102.listener.ActivityInitListener;

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月1日20:13:55          lzx              Activity 基类
 * *******************************************************************************************
 */
public abstract class BaseActivity extends FragmentActivity implements ActivityInitListener {
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
        try {
            setContentView(getContentView());
            onViewsDidLoad(savedInstanceState);
        } catch (Exception e) {
            e.getStackTrace();
        }
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
    }

    //跳转到主界面
    public void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
