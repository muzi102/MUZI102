package com.nxm.muzi102.activity.register

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity
import com.nxm.muzi102.comment.AppConstant
import com.nxm.muzi102.utils.CKey
import com.nxm.muzi102.utils.CommonUtils
import com.nxm.muzi102.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.titlebar.*
import java.lang.ref.WeakReference

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月8日00:52:24             lzx              RegisterActivity布局界面
 * 2018年1月16日10:51:18            lzx              添加手机号码验证
 * *******************************************************************************************
 */
class RegisterActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private lateinit var mHandler: Handler1
    override fun getContentView(): Int {
        return R.layout.activity_register
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        //初始化参数
        titlebar_title.text = getString(R.string.reginster_title)
        mHandler = Handler1(this@RegisterActivity)
        initListener()
    }

    //初始化监听
    private fun initListener() {
        reginster_tv_agreement.setOnClickListener(this)
        titlebar_back.setOnClickListener(this)
        reginster_seekbar.setOnSeekBarChangeListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.titlebar_back -> finish()
        }
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
                if (CommonUtils.getInstance().isMobile(reginster_et_phone.text.toString())) {
                    //是手机号码，跳转短信验证
                    ToastUtil.toast(this@RegisterActivity, getString(R.string.reginster_5))
                    mHandler.sendEmptyMessageDelayed(CKey.WHAT_ONE, 2000)

                } else {
                    //提示填写手机号码
                    ToastUtil.toast(this@RegisterActivity, getString(R.string.reginster_4))
                    mHandler.sendEmptyMessageDelayed(CKey.WHAT_TWO, 2000)
                }
            }
        }
    }

    /**
     * handler消息处理类
     */
    private class Handler1(activity: RegisterActivity) : Handler() {
        private val mActivity: WeakReference<RegisterActivity> = WeakReference(activity)
        val activity = mActivity.get()
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg != null) {
                when (msg.what) {
                    CKey.WHAT_ONE -> activity!!.gotoVerifyActivity()
                    CKey.WHAT_TWO -> activity!!.reginster_seekbar.progress = 0
                }
            }
        }
    }

    /**
     *跳转到VerifyActivity界面
     */
    private fun gotoVerifyActivity() {
        VerifyActivityActivity.Companion.actionStart(this@RegisterActivity, AppConstant.ONE, reginster_et_phone.text.toString())
        reginster_seekbar.progress = 0
    }
}



