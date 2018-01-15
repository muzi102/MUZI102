package com.nxm.muzi102.activity.register

import android.os.Bundle
import android.view.View
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_verify_activity.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                         功能或Bug描述
 * 2018年1月7日23:35:51            lzx              VerifyActivityActivity布局界面
 * 2018年1月10日23:22:03           lzx              添加点击监事件
 * *******************************************************************************************
 */
class VerifyActivityActivity : BaseActivity(), View.OnClickListener {
    override fun getContentView(): Int {
        return R.layout.activity_verify_activity
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.setText(getString(R.string.verify_title))
        verify_next.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.verify_next ->
                goToAccountInfoActivity()
        }
    }

    private fun goToAccountInfoActivity() {
        mIntent.setClass(this@VerifyActivityActivity, AccountInfoActivity::class.java)
        startActivity(mIntent)
    }
}
