package com.nxm.muzi102.listener;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Msg: view 初始化监听器
 */
public interface ActivityInitListener {

    /**
     * 获取显示的view
     */
    @LayoutRes
    int getContentView();

    /**
     * 控件初始化后执行
     */
    void onViewsDidLoad(Bundle savedInstanceState);
}