package com.example.yxm.photogenic.base.adapter

import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


/**
 *Created by yxm on 2020/2/14
 *@function base ViewPager Fragment adapter
 */
abstract class BaseFragmentAdapter constructor(fm: FragmentManager, titles: Array<String>) : PagerAdapter() {

    protected val TAG = "FragmentPagerAdapter"
    private val mFragmentManager: FragmentManager? = fm
    private var mCurTransaction: FragmentTransaction? = null
    private var mCurrentPrimaryItem: Fragment? = null
    protected val title: Array<String> = titles

    abstract fun getItem(position: Int): Fragment


    override fun startUpdate(@NonNull container: ViewGroup) {
        if (container.id == -1) {
            throw IllegalStateException("ViewPager with adapter $this requires a view id")
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager?.beginTransaction()
        }
        val name = title[position]
        var fragment = this.mFragmentManager?.findFragmentByTag(name)
        if (fragment != null) {
            this.mCurTransaction?.attach(fragment)
        } else {
            fragment = this.getItem(position)
            this.mCurTransaction?.add(container.id, fragment, name)
        }

        if (fragment !== this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false)
            fragment.userVisibleHint = false
        }
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, instance: Any) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager?.beginTransaction()
        }

        this.mCurTransaction?.detach(instance as Fragment)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        val fragment = `object` as Fragment
        if (fragment !== this.mCurrentPrimaryItem) {
            if (this.mCurrentPrimaryItem != null) {
                val temp = mCurrentPrimaryItem
                temp?.setMenuVisibility(false)
                temp?.userVisibleHint = false
            }

            fragment.setMenuVisibility(true)
            fragment.userVisibleHint = true
            this.mCurrentPrimaryItem = fragment
        }

    }

    override fun finishUpdate(container: ViewGroup) {
        if (this.mCurTransaction != null) {
            val temp = this.mCurTransaction
            temp?.commitNowAllowingStateLoss()
            this.mCurTransaction = null
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (`object` as Fragment).view === view
    }

    override fun saveState(): Parcelable? {
        return null
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun makeFragmentName(viewId: Int, id: Long): String {
        return "android:switcher:$viewId:$id"
    }

}