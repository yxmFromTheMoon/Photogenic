package com.example.yxm.photogenic.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.simple.SimpleComponent

/**
 * Created by yxm on 2020-1-15
 * @function: 自定义刷新头部
 */
class MyRefreshHeader @JvmOverloads constructor(context: Context,attributeSet: AttributeSet,defStyleAttr: Int = 0)
    : SimpleComponent(context,attributeSet,defStyleAttr){

    lateinit var refreshView: ImageView

    init {

    }

    /**
     * @return 延迟500毫秒弹回
     */
    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        return 500
    }

    /**
     * 刷新状态改变
     */
    override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
        when(newState){
            RefreshState.PullDownToRefresh ->{
                //设置下拉动画
            }
            RefreshState.ReleaseToRefresh ->{
                //设置刷新动画
            }
            RefreshState.Refreshing ->{
                //设置加载中动画
            }
            RefreshState.PullDownCanceled ->{
                //下拉取消
            }
        }
    }
}