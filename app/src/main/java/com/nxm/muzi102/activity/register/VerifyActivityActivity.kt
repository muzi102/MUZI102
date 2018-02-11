package com.nxm.muzi102.activity.register

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import com.githang.statusbar.StatusBarCompat
import com.nxm.muzi102.R
import com.nxm.muzi102.activity.BaseActivity
import com.nxm.muzi102.https.httpUtils.NetConnUtil
import com.nxm.muzi102.utils.CKey
import com.nxm.muzi102.utils.LogUtil
import com.nxm.muzi102.utils.SMSSDKUtil
import com.nxm.muzi102.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_verify_activity.*
import kotlinx.android.synthetic.main.titlebar.*
import java.lang.ref.WeakReference

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                         功能或Bug描述
 * 2018年1月7日23:35:51            lzx              VerifyActivityActivity布局界面
 * 2018年1月10日23:22:03           lzx              添加点击监事件
 * 2018年1月16日15:41:25           lzx              完善MOB 短信验证功能
 * 2018年1月17日14:53:02           lzx              处理handler内存泄露问题
 * *******************************************************************************************
 */
class VerifyActivityActivity : BaseActivity(), View.OnClickListener, SMSSDKUtil.SMSSKEventHandlerInterface {
    private lateinit var phone: String
    private lateinit var mThread: Thread
    private lateinit var mHandler1: Handler1
    private lateinit var mHandler: Handler2

    override fun getContentView(): Int {
        return R.layout.activity_verify_activity
    }


    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        //设置状态栏背景和字体颜色
        StatusBarCompat.setStatusBarColor(this, resources.getColor(R.color.colorWhite), true)
        titlebar_title.text = getString(R.string.verify_title)
        //初始化
        initData()
        initListener()
    }

    //初始参数
    private fun initData() {
        phone = intent.getStringExtra(CKey.phone)
        if (!phone.isEmpty()) {
            verify_tv_phont.text = phone
            SMSSDKUtil.getInstance().smsskEventHandlerInterface = this
            //初始化thread
            mThread = object : Thread() {
                override fun run() {
                    super.run()
                    LogUtil.e("mThread", "toString()")
                    for (i in 120 downTo 0) {
                        LogUtil.e("mThread", i.toString())
                        mHandler.obtainMessage(CKey.ZERO, i).sendToTarget()
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                            break
                        }

                    }
                }
            }
        } else {
            finish()
        }
        //声明handler
        mHandler1 = Handler1(this)
        mHandler = Handler2(this)
    }

    //初始化监听
    private fun initListener() {
        verify_next.setOnClickListener(this)
        titlebar_back.setOnClickListener(this)
        verify_tv_again.setOnClickListener(this)
        verify_line1.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
        //下一步
            R.id.verify_next -> {
                if (verify__tv_checkcode.text.toString().isEmpty() || verify__tv_checkcode.text.toString().length != 6) {
                    ToastUtil.toast(this@VerifyActivityActivity, "请填写正确的验证码")
                } else {
                    if (NetConnUtil.isNetworkAvailable(this@VerifyActivityActivity)) {
                        //发送验证码
                        SMSSDKUtil.getInstance().submitCode("86", phone, verify__tv_checkcode.text.toString())
                        verify_next.isEnabled = false
                    } else {
                        ToastUtil.toastNteNeed(this@VerifyActivityActivity)
                    }
                }
            }
        //返回键
            R.id.titlebar_back -> finish()
        //获取验证码
            R.id.verify_tv_again -> {
                if (NetConnUtil.isNetworkAvailable(this@VerifyActivityActivity)) {
                    SMSSDKUtil.getInstance().sendCode("86", phone)
                    verify_tv_again.isEnabled = false
                } else {
                    ToastUtil.toastNteNeed(this@VerifyActivityActivity)
                }
            }
            R.id.verify_line1 -> {
                goToAccountInfoActivity()
            }

        }
    }

    /**
     * 短信验证结果回调
     * CKey.WHAT_ONE-发送成功 ，CKey.WHAT_TWO-发送失败 ， CKey.WHAT_THERE-验证成功 ，CKey.WHAT_FOUR-验证失败
     */
    override fun SMSSDKResult(result: Int, textContent: String?) {
        val mMessage = Message()
        mMessage.what = result
        mMessage.obj = textContent
        mHandler.sendMessage(mMessage)
    }

    private fun goToAccountInfoActivity() {
        mIntent.setClass(this@VerifyActivityActivity, AccountInfoActivity::class.java)
        mIntent.putExtra(CKey.phone, phone)
        startActivity(mIntent)
        mHandler.removeCallbacksAndMessages(null)
        mHandler1.removeCallbacksAndMessages(null)
        if (!mThread.isInterrupted)
            mThread.interrupt()
        finish()
    }

    private class Handler2(activity: VerifyActivityActivity) : Handler() {
        private val mActivity: WeakReference<VerifyActivityActivity> = WeakReference(activity)
        val activity = mActivity.get()
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg != null) {
                when (msg.what) {
                    CKey.WHAT_ONE -> {
                        activity!!.verify_tv_again.text = "秒重新获取"
                        //延迟读秒
                        if (!activity.mThread.isAlive)
                            activity.mThread.start()
                        ToastUtil.toast(activity, msg.obj as String?)
                    }
                    CKey.WHAT_TWO -> {
                        activity!!.verify_tv_again.text = "获取验证码"
                        activity.verify_tv_again.isEnabled = true
                        activity.verify_next.isEnabled = true
                        activity.verify_tv_time.text = ""
                        //重新发送
                        ToastUtil.toast(activity, msg.obj as String?)
                    }
                    CKey.WHAT_THERE -> {
                        //延迟执行跳转
                        activity!!.mHandler1.sendEmptyMessageDelayed(CKey.WHAT_TWO, 2000)
                        ToastUtil.toast(activity, msg.obj as String?)
                    }
                    CKey.WHAT_FOUR -> {
                        activity!!.verify_next.isEnabled = true
                        ToastUtil.toast(activity, msg.obj as String?)
                    }
                    CKey.ZERO -> {
                        val time: Int = msg.obj as Int
                        if (time > 0) {
                            activity!!.verify_tv_time.text = time.toString()
                        } else {
                            activity!!.verify_tv_time.text = ""
                            activity.verify_tv_again.text = "获取验证码"
                            activity.verify_tv_again.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private class Handler1(activity: VerifyActivityActivity) : Handler() {
        private val mActivity: WeakReference<VerifyActivityActivity> = WeakReference(activity)
        val activity = mActivity.get()
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg != null) {
                when (msg.what) {
                    CKey.WHAT_TWO -> {
                        activity!!.verify_tv_again.text = "获取验证码"
                        activity.verify_tv_time.text = ""
                        activity.verify_next.isEnabled = true
                        activity.verify_tv_again.isEnabled = true
                        activity.goToAccountInfoActivity()
                    }
                }
            }
        }
    }
}
