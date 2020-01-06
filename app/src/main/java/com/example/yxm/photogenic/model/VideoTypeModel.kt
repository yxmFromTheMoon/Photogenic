package com.example.yxm.photogenic.model

import com.example.lib_network.api.RetrofitManager
import com.example.lib_network.api.constants.UrlConstants
import com.example.lib_network.api.constants.VideoType
import com.example.yxm.photogenic.rxschedulers.IoMainScheduler
import io.reactivex.Observable

/**
 * Created by yxm on 2020-1-6
 * @function: 视频类型model
 */
class VideoTypeModel {

    /**
     * 广告类型
     */
    fun getAdVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.AD)
                .compose(IoMainScheduler())
    }

    /**
     * 生活类型
     */
    fun getLifeVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.LIFE)
                .compose(IoMainScheduler())
    }

    /**
     * 动画类型
     */
    fun getCartoonVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.CARTOON)
                .compose(IoMainScheduler())
    }

    /**
     * 搞笑类型
     */
    fun getFunnyVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.FUNNY)
                .compose(IoMainScheduler())
    }

    /**
     * 开胃类型
     */
    fun getFoodVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.FOOD)
                .compose(IoMainScheduler())
    }

    /**
     * 创意类型
     */
    fun getCreativeVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.CREATIVE)
                .compose(IoMainScheduler())
    }

    /**
     * 运动类型
     */
    fun getSportVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.SPORT)
                .compose(IoMainScheduler())
    }

    /**
     * 音乐类型
     */
    fun getMusicVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.MUSIC)
                .compose(IoMainScheduler())
    }

    /**
     * 萌宠类型
     */
    fun getPetsVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.PETS)
                .compose(IoMainScheduler())
    }

    /**
     * 剧情类型
     */
    fun getPlotVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.PLOT)
                .compose(IoMainScheduler())
    }

    /**
     * 科技类型
     */
    fun getTechnologyVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.TECHNOLOGY)
                .compose(IoMainScheduler())
    }

    /**
     * 旅游类型
     */
    fun getTravelVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.TRAVEL)
                .compose(IoMainScheduler())
    }

    /**
     * 影视类型
     */
    fun getMovieVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.MOVIE)
                .compose(IoMainScheduler())
    }

    /**
     * 记录类型
     */
    fun getMomentVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.MOMENT)
                .compose(IoMainScheduler())
    }

    /**
     * 游戏类型
     */
    fun getGameVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.GAME)
                .compose(IoMainScheduler())
    }

    /**
     * 综艺类型
     */
    fun getShowVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.VARIETY_SHOW)
                .compose(IoMainScheduler())
    }

    /**
     * 时尚类型
     */
    fun getFashionVideo():Observable<Any>{
        return RetrofitManager.getApi(UrlConstants.baseUrlApiOpen)
                .getVideoRecommend(VideoType.FASHION)
                .compose(IoMainScheduler())
    }
}