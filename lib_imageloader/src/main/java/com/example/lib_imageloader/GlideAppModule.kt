package com.example.lib_imageloader

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

/**
 * @author yxm
 * 2020/4/21 16:27
 * @function
 */
@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryCacheSizeBytes = 1024 * 1024 * 60 // 60mb
        val diskCacheSizeBytes = 1024 * 1024 * 100 //100 MB
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
                .setDiskCache(InternalCacheDiskCacheFactory(context, diskCacheSizeBytes.toLong()))
                .setDefaultRequestOptions(RequestOptions()
                        .placeholder(R.mipmap.blackholder)
                        .error(R.mipmap.blackholder)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .setDefaultTransitionOptions(Drawable::class.java, DrawableTransitionOptions.withCrossFade())
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}