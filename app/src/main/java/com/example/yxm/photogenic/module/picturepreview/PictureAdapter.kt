package com.example.yxm.photogenic.module.picturepreview

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.yxm.photogenic.base.BaseFragment

/**
 * @author yxm
 * 2020/4/21 13:51
 * @function 查看图片adapter
 */
class PictureAdapter(fm: FragmentManager, fragments: ArrayList<BaseFragment>) : FragmentStatePagerAdapter(fm) {

    private val mFragments = fragments

    override fun getItem(p0: Int): Fragment {
        return mFragments[p0]
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}