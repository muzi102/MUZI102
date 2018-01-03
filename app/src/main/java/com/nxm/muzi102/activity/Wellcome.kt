package com.nxm.muzi102.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.nxm.muzi102.R
import com.nxm.muzi102.comment.Constants

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月1日20:14:10            lzx              Activity 基类
 */
class Wellcome : BaseActivity() {
    //声明变量
    private lateinit var handler: Handler
    private lateinit var myThread: Thread

    override fun getContentView(): Int {
        return R.layout.activity_wellcome
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        initData()
    }

    fun initData() {
        //初始化myThread
        myThread = object : Thread() {
            override fun run() {
                super.run()
                handler.sendEmptyMessageDelayed(Constants.ZERO, 2000)
            }
        }
        //初始化handler
        handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                if (msg?.what == Constants.ZERO) {
                    var intent = Intent()
                    intent.setClass(this@Wellcome, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!myThread.isAlive)
            myThread.start()
    }

    override fun onStop() {
        super.onStop()
        //判断线程是否仍活着
        if (myThread.isAlive) {
            myThread.interrupt()
        }
    }
    //明天会更好
}