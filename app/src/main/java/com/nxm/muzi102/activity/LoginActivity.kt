package com.nxm.muzi102.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nxm.muzi102.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月6日00:18:22             lzx              loginActivity布局界面
 * 2018年1月8日13:39:59             LZX               修复Thread线程问题
 * *******************************************************************************************
 */
class LoginActivity : BaseActivity(), View.OnClickListener {
    private var mIntent: Intent = Intent()
    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        titlebar_title.setText(getString(R.string.login_title))
        initOnClick()
    }

    private fun initOnClick() {
        login_btn_register.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.login_btn_register ->
                goToRegister()
        }
    }

    private fun goToRegister() {
        mIntent.setClass(this@LoginActivity, RegisterActivity::class.java)
        startActivity(mIntent)
    }
}
