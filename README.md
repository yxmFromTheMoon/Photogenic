### 写这个项目的初衷
 最近也是刚刚学了一下kotlin，用了一下MVP架构，感觉还不错，比我之前接触的MVC架构数据流更加清晰，甚至都不用写注释(前提是命名规范)，但也有它的缺点，接口太多，有的接口不需要做任何事情却不得不去实现，这显得有点多余，后面再去了解一下MVVM。总之，不论是MVC还是MVP，没有孰好孰坏之分，只要适合自己项目的架构就是一个好架构。另外安利一波“原版”《开眼》App,内容挺不错的，UI风格也比较前卫，我已经很尽力的去模仿了，奈何没有UI设计天分的我。

### 项目介绍
 整体架构，Kotlin + MVP + Retrofit + RxJava2 + Glide，暂时只有五个模块，app,网络请求，图片加载，分享，保活(没用到)，后续还会增加一些其他通用功能，进行组件化路由改造。还用到一些开源UI库，这里就不一一列举了。某些图片是反编译了开眼拿到的
### 项目截图
<img src="https://github.com/yxmFromTheMoon/Photogenic/blob/master/screenshot/1.png" width="300" height="600" alt="首页">  <img src="https://github.com/yxmFromTheMoon/Photogenic/blob/master/screenshot/2.png" width="300" height="600" alt="社区">

<img src="https://github.com/yxmFromTheMoon/Photogenic/blob/master/screenshot/3.png" width="300" height="600" alt="发现">  <img src="https://github.com/yxmFromTheMoon/Photogenic/blob/master/screenshot/4.png" width="300" height="600" alt="视频播放页">

### 感谢开源数据支持，开源，让你变得更强大

### 附二维码
![image](https://github.com/yxmFromTheMoon/Photogenic/blob/master/screenshot/photogenic_qr_code.png)

### License

Copyright (c) 2020 RobinYxm(尹向明)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
