package com.nxm.muzi102.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.register.RegisterActivity
import com.nxm.muzi102.utils.LogUtil
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
    private val TAG: String = "LoginActivity"
    override fun getContentView(): Int {
        return R.layout.activity_login
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        initData()
        initOnClick()
    }

    /**
     * 初始化参数
     */
    private fun initData() {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.setText(getString(R.string.login_title))
    }

    /**
     * 设置点击事件
     */
    private fun initOnClick() {
        login_btn_register.setOnClickListener(this)
        login_forget_psw.setOnClickListener(this)
    }

    /**
     * 点击事件逻辑
     */
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.login_btn_register ->
                goToRegister()
            R.id.login_forget_psw -> {
//                AccountCenterActivity.Companion.actionStart(this@LoginActivity, AppConstant.ONE)
                LogUtil.e(TAG, "执行了")
            }
        }
    }

    /**
     * 跳转到注册页面
     */
    private fun goToRegister() {
        mIntent.setClass(this@LoginActivity, RegisterActivity::class.java)
        startActivity(mIntent)
    }

    companion object {
        fun actionStart(context: Context) {
            var intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}
