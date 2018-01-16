package com.nxm.muzi102.activity.register

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity
import com.nxm.muzi102.utils.CKey
import com.nxm.muzi102.utils.SMSSDKUtil
import com.nxm.muzi102.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_verify_activity.*
import kotlinx.android.synthetic.main.titlebar.*

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                         功能或Bug描述
 * 2018年1月7日23:35:51            lzx              VerifyActivityActivity布局界面
 * 2018年1月10日23:22:03           lzx              添加点击监事件
 * 2018年1月16日15:41:25           lzx              完善MOB 短信验证功能
 * *******************************************************************************************
 */
class VerifyActivityActivity : BaseActivity(), View.OnClickListener, SMSSDKUtil.SMSSKEventHandlerInterface {

    private lateinit var phone: String
    override fun getContentView(): Int {
        return R.layout.activity_verify_activity

    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.setText(getString(R.string.verify_title))
        initData()
        //初始化
        initListener()
    }

    //初始参数
    private fun initData() {
        phone = intent.getStringExtra(CKey.phone)
        if (!phone.isEmpty()) {
            verify_tv_phont.text = phone
            SMSSDKUtil.getInstance().smsskEventHandlerInterface = this
        } else {
            finish()
        }
    }

    //初始化监听
    private fun initListener() {
        verify_next.setOnClickListener(this)
        titlebar_back.setOnClickListener(this)
        verify_tv_again.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
        //下一步
            R.id.verify_next -> {
                if (verify__tv_checkcode.text.toString().isEmpty() || verify__tv_checkcode.text.toString().length != 6) {
                    ToastUtil.toast(this@VerifyActivityActivity, "请填写正确的验证码")
                } else {
                    //发送验证码
                    SMSSDKUtil.getInstance().submitCode("86", phone, verify__tv_checkcode.text.toString())
                    verify_next.isEnabled = false
                }
            }
        //返回键
            R.id.titlebar_back -> finish()
        //获取验证码
            R.id.verify_tv_again -> {
                SMSSDKUtil.getInstance().sendCode("86", phone)
                verify_tv_again.isEnabled = false
            }

        }
    }

    /**
     * 短信验证结果回调
     * CKey.WHAT_ONE-发送成功 ，CKey.WHAT_TWO-发送失败 ， CKey.WHAT_THERE-验证成功 ，CKey.WHAT_FOUR-验证失败
     */
    override fun SMSSDKResult(result: Int, textContent: String?) {
        var mMessage = Message()
        mMessage.what = result
        mMessage.obj = textContent
        mHandler.sendMessage(mMessage)
    }

    private fun goToAccountInfoActivity() {
        mIntent.setClass(this@VerifyActivityActivity, AccountInfoActivity::class.java)
        startActivity(mIntent)
        mHandler.removeCallbacksAndMessages(null)
        finish()
    }

    private var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg != null) {
                when (msg.what) {
                    CKey.WHAT_ONE -> {
                        verify_tv_again.text = "秒后从新获取"
                        verify_tv_time.text = "120"
                        //延迟读秒

                    }
                    CKey.WHAT_TWO -> {
                        verify_tv_again.text = "获取验证码"
                        verify_tv_again.isEnabled = true
                        verify_next.isEnabled = true
                        verify_tv_time.text = ""
                        //重新发送
                    }
                    CKey.WHAT_THERE -> {
                        //延迟执行跳转
                        postDelayed(Runnable {
                            verify_tv_again.text = "获取验证码"
                            verify_tv_time.text = ""
                            goToAccountInfoActivity()
                        }, 2000)
                    }
                    CKey.WHAT_FOUR -> {
                        verify_next.isEnabled = true
                    }
                }
                ToastUtil.toast(this@VerifyActivityActivity, msg.obj as String?)
            }
        }
    }
}
