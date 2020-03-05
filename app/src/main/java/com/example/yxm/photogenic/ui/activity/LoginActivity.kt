package com.example.yxm.photogenic.ui.activity

import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseActivity
import com.gyf.immersionbar.ktx.immersionBar
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by yxm on 2020-1-11
 * @function: 登录Activity
 */
class LoginActivity : BaseActivity() {

    private lateinit var login: TextView
    private lateinit var userNameEdit: EditText
    private lateinit var passwordEdit: EditText

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }


    override fun initData() {
        Log.i("", "")
    }

    override fun initView() {
        login = login_action
        userNameEdit = username_ed
        passwordEdit = password_ed
    }

    override fun initListener() {
        login.setOnClickListener {
            val username = userNameEdit.text.toString()
            if (username.isEmpty()) {
                showToast("随便输一个呗")
            } else {
                EventBus.getDefault().post(username)
                finish()
            }
        }
    }

    override fun setStatusBarState() {
        immersionBar {
            statusBarColor("#000000")
        }
    }

}