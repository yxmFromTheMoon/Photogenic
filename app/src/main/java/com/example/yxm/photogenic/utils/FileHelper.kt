package com.example.yxm.photogenic.utils

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.yxm.photogenic.application.MyApplication
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 *Created by yxm on 2020/3/12
 *@function 文件操作帮助类
 */
object FileHelper {

    private val mContext = MyApplication.instance

    /**
     * 保存图片到本地
     */
    private fun saveImageToGallery(bitmap: Bitmap) {
        val fileDir = File(Environment.getExternalStorageDirectory(), "Photogenic")
        if (!fileDir.exists()) {
            fileDir.mkdir()
        }
        val fileName = "${System.currentTimeMillis()}" + ".jpg"
        val file = File(fileDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        //插入本地图库,此段代码也会将图片添加到系统默认的pictures文件夹下，
        //手机中就会有两张相同的图片，分别在不同的文件夹中
//        try {
//            MediaStore.Images.Media.insertImage(mContext.contentResolver,
//                    file.absolutePath, fileName, null)
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
        //通知图库更新
        val uri = Uri.fromFile(file)
        mContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
            data = uri
        })
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
                        saveImageToGallery(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
    }
}