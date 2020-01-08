package com.example.yxm.photogenic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.yxm.photogenic.model.DiscoveryModel
import com.example.yxm.photogenic.utils.TextUtils
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        val password: TextView = password
        val password2: TextView = password2

        password.text = TextUtils.justifyString("密码密码",5)
        password2.text = TextUtils.justifyString("大大密码吗",5)

        val model = DiscoveryModel()
        Log.i("MainActivity",model.getBannerList().toString())
    }
}
