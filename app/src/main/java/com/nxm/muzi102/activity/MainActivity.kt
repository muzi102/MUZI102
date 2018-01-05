package com.nxm.muzi102.activity

import android.os.Bundle
import com.nxm.muzi102.R

/**
 * **************************************************************************************************************
 * 修改日期                         修改人             任务名称                        功能或Bug描述
 * 2018年1月1日22:26:55             lzx              MainActivity 基类                   主界面
 * 2018年1月4日22:14:56            lzx               添加6.0以上版本铭感权限申请
 * *************************************************************************************************************
 */
class MainActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
    }
}
