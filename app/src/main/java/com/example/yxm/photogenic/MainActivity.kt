package com.example.yxm.photogenic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lib_network.bean.BannerDataBean
import com.example.lib_network.okhttp.gsonutils.GsonUtils
import com.example.yxm.photogenic.jsonview.ViewBanner
import com.example.yxm.photogenic.model.DiscoveryModel

class MainActivity : AppCompatActivity(),DiscoveryModel.DiscoveryModelListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        val model = DiscoveryModel()
        model.setDiscoveryModelListener(this)
        val viewbanner = ViewBanner(this)
    }

    override fun onGetBannerData(jsonString: String) {
        val bean = GsonUtils.jsonStringToBean(jsonString,BannerDataBean::class.java)
        Log.i("MainActivity","${bean.count}")
    }
}
