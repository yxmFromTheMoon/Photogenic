package com.example.yxm.photogenic.module.discovery

import com.example.lib_network.bean.BannerDataBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.lib_network.okhttp.gsonutils.GsonUtils
import com.example.yxm.photogenic.base.BasePresenter
import com.example.yxm.photogenic.model.DiscoveryModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by yxm on 2020-1-14
 * @function:发现presenter层
 */
class DiscoveryPresenter : BasePresenter<DiscoveryContract.IDiscoveryView>(), DiscoveryContract.IDiscoveryPresenter {

    private val discoveryModel: DiscoveryModel by lazy {
        DiscoveryModel()
    }

    /**
     * 获取banner数据
     */
    override fun getBannerData() {
        checkViewAttached()
        mRootView?.showLoading()
        val dispose = discoveryModel.getDiscoveryData()
                .flatMap {
                    getDataTypeObservable(it,"horizontalScrollCard")
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mRootView?.apply {
                        finishRefresh()
                        val bannerList = ArrayList<BannerDataBean>()
                        val urlList = ArrayList<String>()
                        it.forEach { jsonObject ->
                            bannerList.add(GsonUtils.jsonStringToBean(jsonObject.toString(), BannerDataBean::class.java))
                        }
                        bannerList.forEach { bannerDataBean ->
                            bannerDataBean.itemList.forEach { bannerItemBean ->
                                urlList.add(bannerItemBean.data.image)
                            }
                        }
                        setBannerList(urlList)
                    }
                }, {
                    mRootView?.apply {
                        finishRefresh()
                        showError("Banner获取失败")
                    }
                })
        addSubscribe(dispose)
    }

    /**
     * 获得videoCollectionWithBrief类型的数据
     */
    override fun getVideoCollectionWithBrief() {
        checkViewAttached()
        mRootView?.showLoading()
        val dispose = discoveryModel.getDiscoveryData()
                .flatMap {
                    getDataTypeObservable(it,"videoCollectionWithBrief")
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mRootView?.apply {
                        val mVideoList: ArrayList<CommonVideoBean.ResultBean.ResultData> = ArrayList()
                        finishRefresh()
                        it.forEach { jsonObject ->
                            val resultList: JSONArray = jsonObject.getJSONArray("itemList")
                            for (i in 0 until resultList.length()){
                                val json = resultList.getJSONObject(i)
                                mVideoList.add(GsonUtils.jsonStringToBean(json.getJSONObject("data").toString(), CommonVideoBean.ResultBean.ResultData::class.java))
                            }
                        }
                        setVideoData(mVideoList)
                    }
                }, {
                    mRootView?.apply {
                        finishRefresh()
                        showError("videoCollectionWithBrief获取失败")
                    }
                })
        addSubscribe(dispose)
    }

    /**
     * 获取分类
     */
    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val dispose = discoveryModel.getCategoryData()
                .subscribe({
                    mRootView?.apply {
                        finishRefresh()
                        dismissLoading()
                        setCategory(it)
                    }
                },{
                    mRootView?.apply {
                        finishRefresh()
                        showError("获取分类列表失败")
                    }
                })
        addSubscribe(dispose)
    }

    /**
     * 获取不同类型的数据
     * @responseBody 返回的数据结构体
     * @type 要转换的类型
     * @return 转换类型的JSONObject集合
     */
    private fun getDataTypeObservable(responseBody: ResponseBody,type: String): Observable<ArrayList<JSONObject>> {
        return Observable.create(object : ObservableOnSubscribe<ArrayList<JSONObject>> {
            val result = responseBody.string().run {
                JSONObject(this)
            }.run {
                getJSONArray("itemList")
            }.run {
                val list: ArrayList<JSONObject> = ArrayList()
                for (i in 0 until this.length()) {
                    list.add(this.getJSONObject(i))
                }
                list
            }.run {
                val resultList: ArrayList<JSONObject> = ArrayList()
                filter { jsonObject ->
                    jsonObject.getString("type") == type
                }.forEach { jsonObject ->
                    resultList.add(jsonObject.getJSONObject("data"))
                }
                resultList
            }

            override fun subscribe(emitter: ObservableEmitter<ArrayList<JSONObject>>) {
                emitter.onNext(result)
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io())
    }
}