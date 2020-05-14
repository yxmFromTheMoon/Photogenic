package com.example.yxm.photogenic.module.picturepreview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.yxm.photogenic.base.BaseFragment

/**
 * @author yxm
 * 2020/4/21 13:51
 * @function 查看图片adapter
 */
class PictureAdapter(context: FragmentActivity, fragments: ArrayList<BaseFragment>) : FragmentStateAdapter(context) {

    private val mFragments = fragments

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}