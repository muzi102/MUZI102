package com.nxm.muzi102.activity

import android.os.Bundle
import com.nxm.muzi102.R
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月6日00:18:22             lzx              loginActivity布局界面
 * *******************************************************************************************
 */
class LoginActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        titlebar_title.setText(getString(R.string.login_title))
    }
}
