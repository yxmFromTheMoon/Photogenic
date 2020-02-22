package share.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_share.R

/**
 *Created by yxm on 2020/2/22
 *@function 分享平台adapter
 */
class SharePlatformAdapter: BaseQuickAdapter<PlatformBean,BaseViewHolder>(R.layout.item_share_platform){

    override fun convert(helper: BaseViewHolder, item: PlatformBean) {
        with(helper){
            when(item.name){
                "QQ" -> {
                    setBackgroundRes(R.id.platform_icon,R.drawable.bg_share_qq)
                }
                "QQ空间" -> {
                    setBackgroundRes(R.id.platform_icon,R.drawable.bg_share_qzone)
                }
                "微信" -> {
                    setBackgroundRes(R.id.platform_icon,R.drawable.bg_share_friend)
                }
                "朋友圈" -> {
                    setBackgroundRes(R.id.platform_icon,R.drawable.bg_share_moment)
                }
                "微博" -> {
                    setBackgroundRes(R.id.platform_icon,R.drawable.bg_share_weibo)
                }
            }
            //setImageResource(R.id.platform_icon,item.iconId)
            setText(R.id.platform_name,item.name)
        }
    }
}