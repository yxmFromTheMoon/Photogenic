package share.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.lib_share.R

/**
 *Created by yxm on 2020/2/22
 *@function 分享平台adapter
 */
class SharePlatformAdapter: BaseQuickAdapter<PlatformBean, BaseViewHolder>(R.layout.item_share_platform){

    override fun convert(holder: BaseViewHolder, item: PlatformBean) {
        with(holder){
            when(item.name){
                "QQ" -> {
                    setBackgroundResource(R.id.platform_icon,R.drawable.bg_share_qq)
                }
                "QQ空间" -> {
                    setBackgroundResource(R.id.platform_icon,R.drawable.bg_share_qzone)
                }
                "微信" -> {
                    setBackgroundResource(R.id.platform_icon,R.drawable.bg_share_friend)
                }
                "朋友圈" -> {
                    setBackgroundResource(R.id.platform_icon,R.drawable.bg_share_moment)
                }
                "微博" -> {
                    setBackgroundResource(R.id.platform_icon,R.drawable.bg_share_weibo)
                }
            }
            setText(R.id.platform_name,item.name)
        }
    }
}