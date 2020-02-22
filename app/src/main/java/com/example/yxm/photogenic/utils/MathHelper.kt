package com.example.yxm.photogenic.utils

/**
 *Created by yxm on 2020/2/21
 *@function
 */
object MathHelper {

    /**
     * 获取数组里最大的数
     */
    fun getMaxElem(array: IntArray): Int{
        var maxVal = Int.MIN_VALUE
        for (i in 0 until array.size){
            if(array[i] > maxVal){
                maxVal = array[i]
            }
        }
        return maxVal
    }
}