package com.example.yxm.photogenic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_network.bean.CategoriesBean
import com.example.lib_network.bean.CommonVideoBean
import com.example.yxm.photogenic.R
import com.example.yxm.photogenic.base.BaseImmersionFragment
import com.example.yxm.photogenic.module.discovery.*
import com.example.yxm.photogenic.ui.activity.AllCategoriesActivity
import com.example.yxm.photogenic.ui.activity.AllRankActivity
import com.example.yxm.photogenic.ui.activity.CategoryDetailActivity
import com.example.yxm.photogenic.ui.activity.VideoPlayActivity
import com.example.yxm.photogenic.widget.FooterView
import com.example.yxm.photogenic.widget.RightArrowSelectView
import com.gyf.immersionbar.ImmersionBar
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer
import kotlinx.android.synthetic.main.fragment_discovery.*
import share.core.ShareManager
import java.io.Serializable

/**
 * Created by yxm on 2020-1-14
 * @function:发现fragment
 */
class DiscoveryFragment : BaseImmersionFragment(), DiscoveryContract.IDiscoveryView {

    /**
     * data
     */
    private val mDiscoveryPresenter: DiscoveryPresenter by lazy {
        DiscoveryPresenter()
    }
    private val mCategoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter()
    }
    private val mDiscoveryVideoAdapter: DiscoveryVideoAdapter by lazy {
        DiscoveryVideoAdapter()
    }

    /**
     * UI
     */
    private lateinit var mRefreshLayout: SmartRefreshLayout
    private lateinit var mBanner: Banner<*, *>
    private lateinit var mLookAllCategoryTv: RightArrowSelectView
    private lateinit var mCategoryRv: RecyclerView
    private lateinit var mLookAllFeaturedTv: RightArrowSelectView
    private lateinit var mDiscoveryVideoRv: RecyclerView

    init {
        mDiscoveryPresenter.attachView(this)
    }

    /**
     * 设置分类目录
     */
    override fun setCategory(data: ArrayList<CategoriesBean>) {
        data.removeAt(data.size - 1)
        mCategoryAdapter.setList(data)
    }

    /**
     * 设置本周精选
     */
    override fun setVideoData(data: ArrayList<CommonVideoBean.ResultBean.ResultData>) {
        mDiscoveryVideoAdapter.setList(data)
    }

    /**
     * 设置banner
     */
    override fun setBannerList(data: ArrayList<String>) {

        mBanner.adapter = BannerImageAdapter(data)
        mBanner.setIndicator(CircleIndicator(mContext))
                .setIndicatorGravity(IndicatorConfig.Direction.LEFT)
                .setDelayTime(4000)
                .setPageTransformer(DepthPageTransformer())
                .start()
        mBanner.setOnBannerListener { url, position ->
            showErrorToast("暂无详情( ´◔ ‸◔`)")
        }
    }

    override fun showError(msg: String) {
        showErrorToast(msg)
    }

    override fun showSuccess() {

    }

    override fun finishRefresh() {
        mRefreshLayout.finishRefresh()
    }

    override fun showLoading() {
        mDiscoveryVideoRv.visibility = View.GONE
    }

    override fun dismissLoading() {
        mDiscoveryVideoRv.visibility = View.VISIBLE
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_discovery
    }

    /**
     * 状态栏处理
     */
    override fun initImmersionBar() {
        val statusBarView = status_bar_view
        ImmersionBar.with(this)
                .titleBar(statusBarView)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.statusBarColor)
                .init()
    }

    /**
     * 初始化view
     */
    override fun initView(view: View) {
        mBanner = discovery_banner
        mLookAllCategoryTv = look_all_category
        mLookAllFeaturedTv = look_all_rank
        mCategoryRv = hot_category_rv
        mDiscoveryVideoRv = discovery_video_rv
        mRefreshLayout = refreshLayout

        mBanner.apply {

        }

        mCategoryRv.run {
            layoutManager = GridLayoutManager(mContext, 2, GridLayoutManager.HORIZONTAL, false)
            adapter = mCategoryAdapter
        }

        mDiscoveryVideoRv.run {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mDiscoveryVideoAdapter
        }
        mDiscoveryVideoAdapter.setFooterView(FooterView(mContext))

        mRefreshLayout.setRefreshHeader(ClassicsHeader(mContext))
    }

    override fun initListener() {
        mCategoryAdapter.setOnItemClickListener { adapter, _, position ->
            val categoryBean = adapter.getItem(position) as Serializable
            startActivity(Intent(mContext, CategoryDetailActivity::class.java).apply {
                val bundle = Bundle()
                bundle.putSerializable("categoryBean", categoryBean)
                putExtras(bundle)
            })
        }
        mDiscoveryVideoAdapter.setOnItemClickListener { adapter, _, position ->
            val videoBean = adapter.getItem(position) as CommonVideoBean.ResultBean.ResultData
            startActivity(Intent(mContext, VideoPlayActivity::class.java).apply {
                val bundle = Bundle().apply {
                    putSerializable("video", videoBean)
                    putInt("fromWhere", DISCOVERY)
                    putLong("relativeVideoId", videoBean.id)
                }
                putExtras(bundle)
            })
        }

        //分享
        mDiscoveryVideoAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as CommonVideoBean.ResultBean.ResultData
            if (view.id == R.id.video_share_iv) {
                ShareManager.shareWebPage(mContext,
                        item.description, item.title, item.cover.feed ?: "", item.webUrl.raw)
            }
        }

        mLookAllCategoryTv.setOnClickListener {
            startActivity(Intent(mContext, AllCategoriesActivity::class.java))
        }

        mLookAllFeaturedTv.setOnClickListener {
            startActivity(Intent(mContext, AllRankActivity::class.java))
        }

        mRefreshLayout.setEnableLoadMore(false)
        //下拉刷新
        mRefreshLayout.setOnRefreshListener {
            mDiscoveryPresenter.getBannerData()
            mDiscoveryPresenter.getVideoCollectionWithBrief()
            mDiscoveryPresenter.getCategoryData()
        }

    }

    /**
     * 懒加载数据
     */
    override fun lazyLoad() {
        mDiscoveryPresenter.getBannerData()
        mDiscoveryPresenter.getCategoryData()
        mDiscoveryPresenter.getVideoCollectionWithBrief()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mDiscoveryPresenter.detachView()
    }

    /**
     * 伴生对象
     */
    companion object {
        const val DISCOVERY = 3
        /**
         * 返回一个fragment实例
         */
        fun newInstance(): DiscoveryFragment = DiscoveryFragment()
    }
}