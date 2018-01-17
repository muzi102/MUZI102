package com.nxm.muzi102.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ******************************************************************************************************************
 * 修改日期                             修改人             任务名称                 描述
 * 2018年1月4日23:27:20                 lzx                                        Toast工具
 */

public class ToastUtil {
    public static void toast(Context context, String what) {
        Toast.makeText(context, what, Toast.LENGTH_SHORT).show();
    }

    public static void toastNteNeed(Context context) {
        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
    }
}
