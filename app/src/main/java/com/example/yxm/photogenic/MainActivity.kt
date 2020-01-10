package com.example.yxm.photogenic

import android.os.Bundle
import com.example.yxm.photogenic.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.gyf.immersionbar.ktx.showStatusBar
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login = login_action
        login.setOnClickListener {
            finish()
        }
    }
}
