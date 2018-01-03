package com.nxm.muzi102.utils;

import android.util.Log;

/**
 * ******************************************************************************************************************
 * 修改日期                             修改人             任务名称                 描述
 * 2017年12月18日22:43:48              lzx                                          Log工具
 */
public class LogUtil {

    public static void e(String tag, String name) {
        Log.e(tag, name);
    }   public static void e(String tag, String name,Throwable tr) {
        Log.e(tag, name,tr);
    }

    public static void d(String tag, String name) {
        Log.d(tag, name);
    }
}
