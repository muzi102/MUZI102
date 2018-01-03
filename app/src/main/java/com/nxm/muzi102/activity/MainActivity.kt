package com.nxm.muzi102.activity

import android.os.Bundle
import com.nxm.muzi102.R
import com.nxm.muzi102.base.BuzProfession
import com.nxm.muzi102.https.httpUtils.HttpHelper.post
import com.nxm.muzi102.https.httpUtils.JsonDataToData
import com.nxm.muzi102.https.httpUtils.RequestListener
import com.nxm.muzi102.utils.LogUtil
import com.squareup.okhttp.Request
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.ArrayList

/**
 * *******************************************************************************************
 * 修改日期                         修改人             任务名称                 功能或Bug描述
 * 2018年1月1日22:26:55             lzx              MainActivity 基类
 */
class MainActivity : BaseActivity() {
    private var url: String = "http://www.baidu.com"
    private var rep: String = "{\\\"code\\\":\\\"200\\\",\\\"data\\\":[{\\\"name\\\":\\\"外科\\\",\\\"id\\\":55,\\\"dictId\\\":3,\\\"value\\\":\\\"1\\\"},{\\\"name\\\":\\\"内科\\\",\\\"id\\\":56,\\\"dictId\\\":3,\\\"value\\\":\\\"2\\\"},{\\\"name\\\":\\\"儿科\\\",\" +\n" +
            "            \"\\\"id\\\":57,\\\"dictId\\\":3,\\\"value\\\":\\\"3\\\"},{\\\"name\\\":\\\"妇产科\\\",\\\"id\\\":58,\\\"dictId\\\":3,\\\"value\\\":\\\"4\\\"},{\\\"name\\\":\\\"精神科\\\",\\\"id\\\":59,\\\"dictId\\\":3,\\\"value\\\":\\\"5\\\"},{\\\"name\\\":\\\"口腔科\\\",\\\"id\\\":60,\\\"dictId\\\":3,\\\"value\\\":\\\"6\\\"},{\\\"name\\\":\\\"耳鼻喉科\\\",\\\"id\\\":61,\\\"dictId\\\":3,\\\"value\\\":\\\"7\\\"}]}\""
    private var buzProfessions = ArrayList<BuzProfession>()
    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        main_tv_hello.text = "nxm"
        getInternetData();

    }


    fun getInternetData() {
        var map = HashMap<String, String>()
        map.put("type", "3")
        post(url, map, object : RequestListener {
            override fun onResponse(response: String?) {
                LogUtil.e("2018", response)
                buzProfessions = JsonDataToData.getInstance().getBuzProfessions(rep)
                LogUtil.e("2018", buzProfessions.size.toString() + "大小")
            }
            override fun onError(request: Request?, e: Exception?) {
                LogUtil.e("2018", e.toString())
            }
        })
    }
}
