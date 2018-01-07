package com.nxm.muzi102.activity

import android.os.Bundle
import com.nxm.muzi102.R
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                         功能或Bug描述
 * 2018年1月7日23:35:51            lzx              VerifyActivityActivity布局界面
 * *******************************************************************************************
 */
class VerifyActivityActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_verify_activity
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        titlebar_title.setText(getString(R.string.verify_title))
    }
}
