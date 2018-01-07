package com.nxm.muzi102.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.nxm.muzi102.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.titlebar.*

class RegisterActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        var mIntent: Intent = Intent()
    }

    override fun getContentView(): Int {
        return R.layout.activity_register
    }

    override fun onViewsDidLoad(savedInstanceState: Bundle?) {
        titlebar_title.setText(getString(R.string.reginster_title))
        reginster_tv_agreement.setOnClickListener(this)
    }

    private fun goToVerifyActivity() {

    }
}
