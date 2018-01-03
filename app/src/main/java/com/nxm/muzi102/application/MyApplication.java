package com.nxm.muzi102.application;

import android.app.Activity;
import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Iterator;
import java.util.Map;

/**
 * 作者： guang 时间： 2015/12/24.
 */
public class MyApplication extends Application {
    public static ArrayMap<String, Activity> map = new ArrayMap();
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

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
