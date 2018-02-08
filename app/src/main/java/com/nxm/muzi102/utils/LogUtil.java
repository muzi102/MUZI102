package com.nxm.muzi102.utils;

import android.util.Log;

/**
 * ******************************************************************************************************************
 * 修改日期                             修改人             任务名称                 描述
 * 2017年12月18日22:43:48              lzx                                          Log工具
 */
public class LogUtil {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static int level = VERBOSE;


    public static void v(String tag, String msg) {
        if (level <= VERBOSE)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (level <= INFO)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (level <= WARN)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (level <= ERROR)
            Log.e(tag, msg, tr);
    }
}
