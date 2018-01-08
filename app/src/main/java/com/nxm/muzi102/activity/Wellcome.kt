package com.nxm.muzi102.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.nxm.muzi102.R
import com.nxm.muzi102.comment.CKey
import com.nxm.muzi102.comment.Constants
import com.nxm.muzi102.permission.CheckPermission
import com.nxm.muzi102.permission.PermissionActivity
import com.nxm.muzi102.utils.ToastUtil

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月1日20:14:10            lzx              Wellcome 基类
 * *******************************************************************************************
 */
class Wellcome : BaseActivity() {
    //声明变量
    private lateinit var handler: Handler
    private lateinit var myThread: Thread
    //请求码
    private val REQUEST_CODE: Int = 0
    private lateinit var checkPermission: CheckPermission
    /**
     * 初始化布局
     */
    override fun getContentView(): Int {
        return R.layout.activity_wellcome
    }

    /**
     * 初始化控件
     */
    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        initData()
    }

    /**
     * 初始化参数
     */
    private fun initData() {
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
                    checkPermission()
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

    /**
     * 跳转到主界面
     */
    private fun gotoMainActivity() {
        var intent = Intent()
        intent.setClass(this@Wellcome, MainActivity::class.java)
        startActivity(intent)
    }


    /**
     * 检查权限
     */
    private fun checkPermission() {
        //请求手机基本信息权限，6.0以上手机需要申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission = CheckPermission(this, this)
            if (checkPermission.checkPermission(this, Constants.PERMISSION)) {
                startPermissionActivity()
            } else {
                goLoginActivity()
            }
        } else {
            goLoginActivity()
        }
    }

    /**
     * 申请权限界面
     */
    private fun startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, *Constants.PERMISSION)
    }

    /**
     * result回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //权限申请回调
        if (requestCode == REQUEST_CODE && resultCode == PermissionActivity.PERMISSION_DENIEG) {
            ToastUtil.toast(this, getString(R.string.noPermissions))
            System.exit(CKey.ZERO)
        } else {
            //权限足够跳转到主页面
//            gotoMainActivity()
            goLoginActivity()
        }
    }

    /**
     * 跳转到页面并且结束
     */
    private fun goLoginActivity() {
        goToLoginActivity()
        finish()
    }
}