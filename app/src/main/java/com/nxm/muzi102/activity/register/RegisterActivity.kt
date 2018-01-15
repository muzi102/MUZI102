package com.nxm.muzi102.activity.register

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity
import com.nxm.muzi102.utils.LogUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月8日00:52:24             lzx              RegisterActivity布局界面
 * *******************************************************************************************
 */
class RegisterActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private var phone: String? = null
    override fun getContentView(): Int {
        return R.layout.activity_register
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        //初始化参数
        titlebar_title.setText(getString(R.string.reginster_title))
        initListener()
    }

    //初始化监听
    private fun initListener() {
        reginster_tv_agreement.setOnClickListener(this)
        reginster_seekbar.setOnSeekBarChangeListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.reginster_tv_agreement ->
                goToVerifyActivity()
        }
    }

    /**
     * 跳转到验证页面
     */
    private fun goToVerifyActivity() {
        mIntent.setClass(this@RegisterActivity, VerifyActivityActivity::class.java)
        startActivity(mIntent)
    }


    /**
     * seekBar监听
     */
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        if (p1 == 0) {
            reginster_text_slide.visibility = View.VISIBLE
        } else {
            reginster_text_slide.visibility = View.GONE
        }
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        if (p0 != null) {
            if (p0.progress <= p0.max / 2) {
                p0.progress = 0
            } else {
                // todo 做滑动到最右的操作.
                p0.progress = p0.max
                checkPhone()
            }
        }
    }

    /**
     * 验证手机号码的正确性
     */
    private fun checkPhone() {
        phone = reginster_et_phone.text.toString()
        LogUtil.e("nxm", "phone" + phone)
    }
}

