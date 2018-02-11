package com.nxm.muzi102.activity.register.forgetpwd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity

import kotlinx.android.synthetic.main.titlebar.*

class AccountCenterActivity : BaseActivity() {
    override fun getContentView(): Int {
        return R.layout.activity_verify_activity
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.text = getString(R.string.accountCenter_title)
        //初始化
        initData()
        initListener()
    }

    private fun initData() {

    }

    private fun initListener() {
    }

    companion object {
        fun actionStart(context: Context) {
            var intent = Intent(context, AccountCenterActivity::class.java)
            context.startActivity(intent)
        }
    }
}
