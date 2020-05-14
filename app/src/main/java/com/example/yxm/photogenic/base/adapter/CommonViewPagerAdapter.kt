package com.example.yxm.photogenic.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 *Created by yxm on 2020/2/14
 *@function 通用viewpager adapter
 */
class CommonViewPagerAdapter(fragmentManager: FragmentManager, titles:Array<String>): BaseFragmentAdapter(fragmentManager,titles){

    private val mFragments = ArrayList<Fragment>()

    //添加fragment
    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
}

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}