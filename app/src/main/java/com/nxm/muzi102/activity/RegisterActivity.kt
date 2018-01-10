package com.nxm.muzi102.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月8日00:52:24             lzx              RegisterActivity布局界面
 * *******************************************************************************************
 */
class RegisterActivity : BaseActivity(), View.OnClickListener {

    override fun getContentView(): Int {
        return R.layout.activity_register
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.setText(getString(R.string.reginster_title))
        reginster_tv_agreement.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.reginster_tv_agreement ->
                goToVerifyActivity()
        }
    }

    private fun goToVerifyActivity() {
        mIntent.setClass(this@RegisterActivity, VerifyActivityActivity::class.java)
        startActivity(mIntent)
    }
}
