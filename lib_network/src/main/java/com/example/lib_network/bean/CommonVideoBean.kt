package com.example.lib_network.bean

/**
 * Created by yxm on 2020-1-6
 * @function: 通用视频实体类
 */
data class CommonVideoBean(val itemList: ArrayList<ResultBean>, val count: Int, val total: Int, val nextPageUrl: String?, val adExist: Boolean) {

    data class ResultBean(val type: String, val data: ResultData, val tag: String?, val id: Int, val adIndex: Int) {

        data class ResultData(val dataType: String, val id: Long, val title: String, val description: String,
                              val library: String, val tags: ArrayList<TagBean>, val consumption: Consumption,
                              val resourceType: String, val slogan: String?, val provider: Provider, val category: String,
                              val author: Author, val cover: Cover, val playUrl: String, val thumbPlayUrl: String?,
                              val duration: Long, val webUrl: WebUrl, val releaseTime: Long, val playInfo: ArrayList<PlayInfoBean>,
                              val campaign: String?, val waterMarks: String?, val ad: Boolean, val adTrack: List<String> = emptyList(),
                              val type: String, val titlePgc: String?, val descriptionPgc: String?, val remark: String,
                              val ifLimitVideo: Boolean, val searchWeight: Int, val brandWebsiteInfo: String?, val idx: Int,
                              val shareAdTrack: String?, val favoriteAdTrack: String?, val webAdTrack: String?, val date: Long,
                              val promotion: String?, val label: String?, val labelList: List<String> = emptyList(), val descriptionEditor: String?,
                              val collected: Boolean, val reallyCollected: Boolean, val played: Boolean, val subtitles: List<String> = emptyList(),
                              val lastViewTime: Long, val playlists: ArrayList<String>?, val src: String?) {

            data class TagBean(val id: Int, val name: String, val actionUrl: String,
                               val adTrack: String?, val desc: String, val bgPicture: String,
                               val headerImage: String, val tagRecType: String, val childTagList: ArrayList<ChildTagBean>?,
                               val childTagIdList: ArrayList<Int>?, val haveReward: Boolean, val ifNewest: Boolean,
                               val newestEndTime: Long?, val communityIndex: Int) {

                data class ChildTagBean(val id: Int)
            }

            data class Consumption(val collectionCount: Long, val shareCount: Long,
                                   val replyCount: Long, val realCollectionCount: Long)

            data class Provider(val name: String, val alias: String, val icon: String)

            data class Author(val id: Int, val icon: String, val name: String, val description: String,
                              val link: String, val latestReleaseTime: Long, val videoNum: Int,
                              val adTrack: String?, val follow: FollowBean, val shield: ShieldBean,
                              val approvedNotReadyVideoCount: Int, val ifPgc: Boolean, val recSort: Int,
                              val expert: Boolean) {

                data class FollowBean(val itemType: String, val itemId: Int, val followed: Boolean)

                data class ShieldBean(val itemType: String, val itemId: Int, val shielded: Boolean)
            }

            data class Cover(val feed: String, val detail: String, val blurred: String,
                             val sharing: String?, val homePage: String)

            data class WebUrl(val raw: String, val forWeibo: String)

            data class PlayInfoBean(val height: Int, val width: Int, val urlList: ArrayList<UrlBean>,
                                    val name: String, val type: String, val url: String) {

                data class UrlBean(val name: String, val url: String, val size: Long)

            }
        }
    }
}
/**
 * bean类
{
    "itemList": [
    {
        "type": "video",
        "data": {
            "dataType": "VideoBeanForClient",
            "id": 182672,
            "title": "跟随这支倒计时混剪，迎接新一年的到来",
            "description": "在电影的倒计时中，庆祝新一年的到来吧，Happy New Year！",
            "library": "DAILY",
            "tags": [
                {
                    "id": 796,
                    "name": "迷影放映室",
                    "actionUrl": "eyepetizer://tag/796/?title=%E8%BF%B7%E5%BD%B1%E6%94%BE%E6%98%A0%E5%AE%A4",
                    "adTrack": null,
                    "desc": "电影、剧集、戏剧抢先看",
                    "bgPicture": "http://img.kaiyanapp.com/64f2b2ed039bd92c3be10d003d6041bf.jpeg?imageMogr2/quality/60/format/jpg",
                    "headerImage": "http://img.kaiyanapp.com/56a8818adb038c59ab04ffc781db2f50.jpeg?imageMogr2/quality/60/format/jpg",
                    "tagRecType": "IMPORTANT",
                    "childTagList": null,
                    "childTagIdList": null,
                    "haveReward": false,
                    "ifNewest": false,
                    "newestEndTime": null,
                    "communityIndex": 0
                },
                {
                "id": 1025,
                "name": "影视",
                "actionUrl": "eyepetizer://tag/1025/?title=%E5%BD%B1%E8%A7%86",
                "adTrack": null,
                "desc": "电影、剧集、戏剧抢先看",
                "bgPicture": "http://img.kaiyanapp.com/8a298964e7c9fc2ae16342832e36d88d.jpeg?imageMogr2/quality/60/format/jpg",
                "headerImage": "http://img.kaiyanapp.com/9d7fe42c1445031e4c8f2421b652a011.jpeg?imageMogr2/quality/60/format/jpg",
                "tagRecType": "NORMAL",
                "childTagList": null,
                "childTagIdList": null,
                "haveReward": false,
                "ifNewest": false,
                "newestEndTime": null,
                "communityIndex": 0
                }
            ],
            "consumption": {
            "collectionCount": 7219,
            "shareCount": 10973,
            "replyCount": 51,
            "realCollectionCount": 1471
            },
            "resourceType": "video",
            "slogan": null,
            "provider": {
            "name": "YouTube",
            "alias": "youtube",
            "icon": "http://img.kaiyanapp.com/fa20228bc5b921e837156923a58713f6.png"
            },
            "category": "影视",
            "author": {
            "id": 2744,
            "icon": "http://img.kaiyanapp.com/f37f2bfffd91a64225c1827ad4762bb2.jpeg?imageMogr2/quality/60/format/jpg",
            "name": "DrMachakil",
            "description": "DrMachakil 只做有趣的电影混剪。",
            "link": "",
            "latestReleaseTime": 1577754000000,
            "videoNum": 29,
            "adTrack": null,
            "follow": {
            "itemType": "author",
            "itemId": 2744,
            "followed": false
            },
            "shield": {
            "itemType": "author",
            "itemId": 2744,
            "shielded": false
            },
            "approvedNotReadyVideoCount": 0,
            "ifPgc": true,
            "recSort": 0,
            "expert": false
        },
        "cover": {
            "feed": "http://img.kaiyanapp.com/d615a21eb90094de9c72d568036ac7ba.jpeg?imageMogr2/quality/60/format/jpg",
            "detail": "http://img.kaiyanapp.com/d615a21eb90094de9c72d568036ac7ba.jpeg?imageMogr2/quality/60/format/jpg",
            "blurred": "http://img.kaiyanapp.com/47840a4abe62ded7d5ec48e9080afe28.jpeg?imageMogr2/quality/60/format/jpg",
            "sharing": null,
            "homepage": "http://img.kaiyanapp.com/d615a21eb90094de9c72d568036ac7ba.jpeg?imageView2/1/w/720/h/560/format/jpg/q/75|watermark/1/image/aHR0cDovL2ltZy5rYWl5YW5hcHAuY29tL2JsYWNrXzMwLnBuZw==/dissolve/100/gravity/Center/dx/0/dy/0|imageslim"
        },
        "playUrl": "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=182672&resourceType=video&editionType=default&source=aliyun&playUrlType=url_oss",
        "thumbPlayUrl": null,
        "duration": 79,
        "webUrl": {
            "raw": "http://www.eyepetizer.net/detail.html?vid=182672",
            "forWeibo": "http://www.eyepetizer.net/detail.html?vid=182672&resourceType=video&utm_campaign=routine&utm_medium=share&utm_source=weibo&uid=0"
        },
        "releaseTime": 1577754000000,
        "playInfo": [
            {
                "height": 720,
                "width": 1280,
                "urlList": [
                    {
                    "name": "aliyun",
                    "url": "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=182672&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss",
                    "size": 6225474
                    },
                    {
                    "name": "ucloud",
                    "url": "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=182672&resourceType=video&editionType=high&source=ucloud&playUrlType=url_oss",
                    "size": 6225474
                    }
                ],
        "name": "高清",
        "type": "high",
        "url": "http://baobab.kaiyanapp.com/api/v1/playUrl?vid=182672&resourceType=video&editionType=high&source=aliyun&playUrlType=url_oss"
        }
        ],
    "campaign": null,
    "waterMarks": null,
    "ad": false,
    "adTrack": [],
    "type": "NORMAL",
    "titlePgc": null,
    "descriptionPgc": null,
    "remark": null,
    "ifLimitVideo": false,
    "searchWeight": 0,
    "brandWebsiteInfo": null,
    "idx": 0,
    "shareAdTrack": null,
    "favoriteAdTrack": null,
    "webAdTrack": null,
    "date": 1577754000000,
    "promotion": null,
    "label": null,
    "labelList": [],
    "descriptionEditor": "在电影的倒计时中，庆祝新一年的到来吧，Happy New Year！",
    "collected": false,
    "reallyCollected": false,
    "played": false,
    "subtitles": [],
    "lastViewTime": null,
    "playlists": null,
    "src": null
    },
    "tag": null,
    "id": 0,
    "adIndex": -1
    },
"count": 10,
"total": 0,
"nextPageUrl": null,
"adExist": false
}
 */