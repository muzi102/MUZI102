package com.nxm.muzi102.activity.register

import android.os.Bundle
import android.view.View
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity
import com.nxm.muzi102.utils.CKey
import com.nxm.muzi102.utils.LogUtil
import com.nxm.muzi102.utils.SMSSDKUtil
import kotlinx.android.synthetic.main.activity_verify_activity.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                         功能或Bug描述
 * 2018年1月10日22:49:53            lzx              AccountInfoActivity设置账号信息
 * *******************************************************************************************
 */
class AccountInfoActivity : BaseActivity(), View.OnClickListener {
    private lateinit var phone: String
    override fun getContentView(): Int {
        return R.layout.activity_account_info
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.text = getString(R.string.accountInfo_title)
        initData()
    }

    private fun initData() {
        phone = intent.getStringExtra(CKey.phone)
        if (phone.isEmpty()) {
            finish()
        }
    }

    override fun onClick(p0: View?) {

    }

}
