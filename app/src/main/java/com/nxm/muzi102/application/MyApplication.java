package com.nxm.muzi102.application;

import android.app.Activity;
import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Iterator;
import java.util.Map;

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月7日15:16:42             lzx              MyApplication类创建
 * *******************************************************************************************
 */
public class MyApplication extends Application {
    public static ArrayMap<String, Activity> map = new ArrayMap();


    @Override
    public void onCreate() {
        super.onCreate();
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
