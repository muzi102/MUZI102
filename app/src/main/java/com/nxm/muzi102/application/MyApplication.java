package com.nxm.muzi102.application;

import android.app.Activity;
import android.app.Application;
import android.support.v4.util.ArrayMap;

import com.mob.MobSDK;

import java.util.Iterator;
import java.util.Map;

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月7日15:16:42             lzx              MyApplication类创建
 * 2018年1月16日15:29:15            lzx              添加MobSDK初始化
 * *******************************************************************************************
 */
public class MyApplication extends Application {
    public static ArrayMap<String, Activity> map = new ArrayMap();


    @Override
    public void onCreate() {
        super.onCreate();
        //通过代码注册你的AppKey和AppSecret
        MobSDK.init(this, "23bfcac96ea64", "267c3db84c228a3c01d35c4d3b2958f0");
    }

    /**
     * 关闭所有的activity
     */
    public static void finishAllActivity() {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            ((Activity) entry.getValue()).finish();
        }
    }
}
