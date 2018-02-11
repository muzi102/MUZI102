package com.nxm.muzi102.utils;

import android.util.Log;

import com.nxm.muzi102.comment.AppConstant;

/**
 * ******************************************************************************************************************
 * 修改日期                             修改人             任务名称                 描述
 * 2017年12月18日22:43:48              lzx                                          Log工具
 */
public class LogUtil {
    public static int level = AppConstant.ONE;

    public static void v(String tag, String msg) {
        if (level <= AppConstant.ONE)
            Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (level <= AppConstant.TWO)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (level <= AppConstant.THREE)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (level <= AppConstant.FOUR)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (level <= AppConstant.FIVE)
            Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (level <= AppConstant.FIVE)
            Log.e(tag, msg, tr);
    }
}
