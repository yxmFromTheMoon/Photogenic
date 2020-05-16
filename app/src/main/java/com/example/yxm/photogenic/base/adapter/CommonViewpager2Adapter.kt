package com.example.yxm.photogenic.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yxm.photogenic.base.BaseFragment

/**
 * @author yxm
 * 2020/4/21 13:51
 * @function 通用viewpager2 adapter
 */
class CommonViewpager2Adapter(context: FragmentActivity, fragments: ArrayList<BaseFragment>) : FragmentStateAdapter(context) {

    private val mFragments = fragments

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }
}