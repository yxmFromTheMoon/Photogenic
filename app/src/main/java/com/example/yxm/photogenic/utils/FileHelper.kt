package com.example.yxm.photogenic.utils

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.yxm.photogenic.application.MyApplication
import java.io.IOException

/**
 *Created by yxm on 2020/3/12
 *@function 文件操作帮助类
 */
object FileHelper {

    private val mContext = MyApplication.instance

    /**
     * 适配Android10作用域存储,需将targetSDK指定为29及以上
     *
     * @param bitmap
     */
    private fun addBitmapToAlbum(bitmap: Bitmap) {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis().toString() + ".jpg")
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        } else {
            contentValues.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_PICTURES + "/" + System.currentTimeMillis() + ".jpg")
        }
        val uri = mContext.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            try {
                mContext.contentResolver.openOutputStream(uri).use { outputStream ->
                    if (outputStream != null) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        outputStream.close()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show()
    }

    /**
     * 使用glide下载图片并保存到本地图库
     */
    fun saveImage(url: String){
        Glide.get(mContext).clearMemory()
        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        addBitmapToAlbum(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
    }
}