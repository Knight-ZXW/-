# -*- coding: utf-8 -*-
from collections import defaultdict, ChainMap
import os

# files = os.listdir('./')
# if any(name.endswith('.py') for name in files):
#     print('there has python')
# else:
#     print('there has not python')

# values = ChainMap()
# values['x'] = 1
# values = values.new_child()
# print(values)
# values['y'] = 2
# values['z'] = 2
# print(values)
# print(values.parents)

# def sample():
#     yield 'A'
#     yield 'B'
#     yield 'C'
#     yield 'D'
# text = ''.join(sample())
# print(text)

# str  = 'I am {name},{year}'
# str.format(name = 'zxw',year=10)
# print(str.format(name = 'zxw',year=10))

# class safesub(dict):
#     def __missing_(self, key):
#         return '{'+ key + '}'
#
# import sys
# def sub(text):
#     #这个需要同函数的栈帧打交代。sys_getframe这个特殊的函数可以让我们获得调用函数的栈信息。
#     return text.format_map(safesub(sys._getframe(1).f_locals))
#
# import textwrap
# name = 'GuidoGuidoGuidoGuido'
# print(textwrap.fill(name,2,subsequent_indent='######'))

# x = 1234
# print(format(x,'b'))


from urllib import request, response
from bs4 import BeautifulSoup, Tag

# with  request.urlopen('https://api.douban.com/v2/book/2129650') as f:
#     data = f.read()
#     print("status", f.status, f.reason)
#     for k, v in f.getheaders():
#         print('%s: %s' % (k, v))
#     print('Data', data.decode('utf-8'))
#
# req = request.Request('http://www.mzitu.com/')
# req.add_header('User-Agent',
#                'Mozilla/6.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/8.0 Mobile/10A5376e Safari/8536.25')
#
# with request.urlopen(req) as f:
#     print("status", f.status, f.reason)
#     for k, v in f.getheaders():
#         print('%s:%s' % (k, v))
#     html = f.read().decode('utf-8')
html_doc = """
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="no-transform">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <meta name="applicable-device" content="pc">
    <title>性感妹子 - 妹子图</title>
    <meta name="keywords" content="性感妹子,性感美女,性感写真图片">
    <meta name="description"
          content="妹子图性感妹子栏目收集性感妹子图片,包括ROSI写真、秀人网、美媛馆、推女神、推女郎、爱蜜社、魅妍社、波萝社、优星馆、嗲囡囡及尤果网等大量性感美女图片及嫩模写真套图。收藏美丽,分享美好!">
    <meta name="renderer" content="webkit">
    <link href="http://pic.mmfile.net/pfiles/main160128.css" rel="stylesheet" type="text/css">
    <link rel="shortcut icon" href="http://pic.mmfile.net/pfiles/img/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon-precomposed" href="http://pic.mmfile.net/pfiles/img/touch-icon-ipad-144.png">
</head>
<body>
<div id="BAIDU_DUP_fp_wrapper"
     style="position: absolute; left: -1px; bottom: -1px; z-index: 0; width: 0px; height: 0px; overflow: hidden; visibility: hidden; display: none;">
    <iframe id="BAIDU_DUP_fp_iframe" src="http://pos.baidu.com/wh/o.htm?ltr="
            style="width: 0px; height: 0px; visibility: hidden; display: none;"></iframe>
</div>
<div class="header">
    <div class="mainnav">
        <h1 class="logo"><a href="http://www.mzitu.com">妹子图</a></h1>
        <ul id="menu-nav" class="menu">
            <li><a title="首页" href="http://www.mzitu.com/">首页</a></li>
            <li class="current-menu-item"><a title="性感妹子" href="http://www.mzitu.com/xinggan">性感妹子</a></li>
            <li><a title="日本妹子" href="http://www.mzitu.com/japan">日本妹子</a></li>
            <li><a title="台湾妹子" href="http://www.mzitu.com/taiwan">台湾妹子</a></li>
            <li><a title="清纯妹子" href="http://www.mzitu.com/mm">清纯妹子</a></li>
            <li><a title="妹子自拍" href="http://www.mzitu.com/share">妹子自拍</a></li>
            <li><a title="每日更新" href="http://www.mzitu.com/all">每日更新</a></li>
        </ul>    <span class="search">
    <form method="get" class="searchform" action="http://www.mzitu.com"><input class="search-input" name="s" type="text"
                                                                               placeholder="搜索是种美德"><button
            class="search-btn" type="submit">搜索</button></form>
    </span>
    </div>
</div>
<div class="main">
    <div class="main-content">
        <div class="currentpath">当前位置:<a href="http://www.mzitu.com">妹子图</a> » 性感妹子</div>
        <div class="postlist">
            <ul id="pins">
                <li><a href="http://www.mzitu.com/63289" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="风情熟女惹火半球 尤美Yumi俏皮妩媚带来无限遐想"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/63289_24b24_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/63289_24b24_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/63289" target="_blank">风情熟女惹火半球 尤美Yumi俏皮妩媚带来无限遐想</a></span><span
                        class="time">12秒前</span><span class="view">3,098次</span></li>
                <li><a href="http://www.mzitu.com/63121" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="清新短发勾人电眼 小妖精Jenny佳妮全裸福利私拍流出"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/63121_23b54_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/63121_23b54_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/63121" target="_blank">清新短发勾人电眼 小妖精Jenny佳妮全裸福利私拍流出</a></span><span
                        class="time">24小时前</span><span class="view">238,633次</span></li>
                <li><a href="http://www.mzitu.com/63075" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="开腿露底不能直视! 极品美女梓萱黑丝美腿制服诱惑"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/63075_21b40_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/63075_21b40_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/63075" target="_blank">开腿露底不能直视! 极品美女梓萱黑丝美腿制服诱惑</a></span><span
                        class="time">2天前</span><span class="view">435,401次</span></li>
                <li><a href="http://www.mzitu.com/62984" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="大奶妹美女杨依湿身入浴,忘情自摸不知走光"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/62984_20a20_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62984_20a20_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/62984" target="_blank">大奶妹美女杨依湿身入浴,忘情自摸不知走光</a></span><span
                        class="time">4天前</span><span class="view">476,607次</span></li>
                <li><a href="http://www.mzitu.com/62939" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="ROSI写真情趣系列 性感美女俯身撅臀福利满满"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/62939_20b20_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62939_20b20_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/62939" target="_blank">ROSI写真情趣系列 性感美女俯身撅臀福利满满</a></span><span
                        class="time">4天前</span><span class="view">265,945次</span></li>
                <li><a href="http://www.mzitu.com/62892" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="翘臀妹子田孝媛性感内衣滚床美艳风骚风情万种"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/62892_19a24_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62892_19a24_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/62892" target="_blank">翘臀妹子田孝媛性感内衣滚床美艳风骚风情万种</a></span><span
                        class="time">5天前</span><span class="view">546,353次</span></li>
                <li><a href="http://www.mzitu.com/62837" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="视觉盛宴! 美胸皇后于姬Una情趣内衣性感火辣"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/62837_18a45_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62837_18a45_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/62837" target="_blank">视觉盛宴! 美胸皇后于姬Una情趣内衣性感火辣</a></span><span
                        class="time">6天前</span><span class="view">418,808次</span></li>
                <li><a href="http://www.mzitu.com/62783" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="清纯妹子赵小米Kitty丁字裤撅臀让你欲火焚身"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/62783_17b20_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62783_17b20_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/62783" target="_blank">清纯妹子赵小米Kitty丁字裤撅臀让你欲火焚身</a></span><span
                        class="time">7天前</span><span class="view">513,981次</span></li>
                <li><a href="http://www.mzitu.com/62728" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="ROSI写真情趣系列 黑丝白领美女巨乳撑爆衬衣"
                                                                              src="http://pic.mmfile.net/thumbs/2016/04/62728_17a19_236.jpg"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62728_17a19_236.jpg"
                                                                              style="display: inline;"></a><span><a
                        href="http://www.mzitu.com/62728" target="_blank">ROSI写真情趣系列 黑丝白领美女巨乳撑爆衬衣</a></span><span
                        class="time">1周前 (04/18)</span><span class="view">461,523次</span></li>
                <li><a href="http://www.mzitu.com/62676" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="好污! 奶油妹子徐cake丰满双乳挑逗姿势令人浮想联翩"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62676_16b20_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62676" target="_blank">好污! 奶油妹子徐cake丰满双乳挑逗姿势令人浮想联翩</a></span><span
                        class="time">1周前 (04/17)</span><span class="view">381,760次</span></li>
                <li><a href="http://www.mzitu.com/62593" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="透着一丝寂寞! 性感女王乐乐Mango火辣身材霸气侧漏"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62593_14b28_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62593" target="_blank">透着一丝寂寞! 性感女王乐乐Mango火辣身材霸气侧漏</a></span><span
                        class="time">1周前 (04/16)</span><span class="view">508,517次</span></li>
                <li><a href="http://www.mzitu.com/62392" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="清纯妹子Evelyn艾莉黑丝制服不慎走光惊爆眼球"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62392_12b20_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62392" target="_blank">清纯妹子Evelyn艾莉黑丝制服不慎走光惊爆眼球</a></span><span
                        class="time">2周前 (04/14)</span><span class="view">273,487次</span></li>
                <li><a href="http://www.mzitu.com/62431" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="软妹田熙玥极品制服秀 狂野又纯情令人充满幻想"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62431_12a04_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62431" target="_blank">软妹田熙玥极品制服秀 狂野又纯情令人充满幻想</a></span><span
                        class="time">2周前 (04/13)</span><span class="view">603,099次</span></li>
                <li><a href="http://www.mzitu.com/62335" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="堪称国产风俗娘! 珍藏级巨乳翘臀美女韩恩熙"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62335_11a23_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62335" target="_blank">堪称国产风俗娘! 珍藏级巨乳翘臀美女韩恩熙</a></span><span
                        class="time">2周前 (04/12)</span><span class="view">550,892次</span></li>
                <li><a href="http://www.mzitu.com/62191" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="妖艳性感尤物Yuli生活照 S线身姿半裸走光勾人欲火"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62191_09a17_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62191" target="_blank">妖艳性感尤物Yuli生活照 S线身姿半裸走光勾人欲火</a></span><span
                        class="time">2周前 (04/11)</span><span class="view">817,817次</span></li>
                <li><a href="http://www.mzitu.com/62284" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="闺蜜之爱 甜美俏佳人夏晓Maggy透明睡衣湿身美不胜收"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62284_10a20_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62284" target="_blank">闺蜜之爱 甜美俏佳人夏晓Maggy透明睡衣湿身美不胜收</a></span><span
                        class="time">2周前 (04/10)</span><span class="view">553,053次</span></li>
                <li><a href="http://www.mzitu.com/62245" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="ROSI写真情趣系列 豪放学生妹紫丝高跟鞋制服诱惑"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62245_10b32_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62245" target="_blank">ROSI写真情趣系列 豪放学生妹紫丝高跟鞋制服诱惑</a></span><span
                        class="time">2周前 (04/10)</span><span class="view">403,842次</span></li>
                <li><a href="http://www.mzitu.com/62140" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="前凸后翘高贵迷人! 绝色私房尤物于姬Una情趣内衣大片"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62140_07a15_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62140" target="_blank">前凸后翘高贵迷人! 绝色私房尤物于姬Una情趣内衣大片</a></span><span
                        class="time">2周前 (04/09)</span><span class="view">580,936次</span></li>
                <li><a href="http://www.mzitu.com/62069" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="彩豆豆Amaris丁字裤风骚秀臀,全裸上阵喷血诱惑"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/62069_06b34_236.jpg"></a><span><a
                        href="http://www.mzitu.com/62069" target="_blank">彩豆豆Amaris丁字裤风骚秀臀,全裸上阵喷血诱惑</a></span><span
                        class="time">3周前 (04/07)</span><span class="view">1,167,541次</span></li>
                <li><a href="http://www.mzitu.com/61950" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="翘臀女王夏茉GIGI巨乳撑爆上衣性感撩人魅力无限"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/61950_05b14_236.jpg"></a><span><a
                        href="http://www.mzitu.com/61950" target="_blank">翘臀女王夏茉GIGI巨乳撑爆上衣性感撩人魅力无限</a></span><span
                        class="time">3周前 (04/06)</span><span class="view">581,293次</span></li>
                <li><a href="http://www.mzitu.com/61859" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="ROSI写真情趣系列 性感女仆爆乳翘臀诱惑无限"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/61859_04b20_236.jpg"></a><span><a
                        href="http://www.mzitu.com/61859" target="_blank">ROSI写真情趣系列 性感女仆爆乳翘臀诱惑无限</a></span><span
                        class="time">3周前 (04/05)</span><span class="view">427,851次</span></li>
                <li><a href="http://www.mzitu.com/61693" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="肤白貌美胸部大,长腿细腰屁股翘 性感女神李七喜"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/61693_01c38_236.jpg"></a><span><a
                        href="http://www.mzitu.com/61693" target="_blank">肤白貌美胸部大,长腿细腰屁股翘 性感女神李七喜</a></span><span
                        class="time">3周前 (04/04)</span><span class="view">903,332次</span></li>
                <li><a href="http://www.mzitu.com/61758" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="美胸女王李宓儿胸猛来袭激情自摸大尺度诱惑"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/61758_02a33_236.jpg"></a><span><a
                        href="http://www.mzitu.com/61758" target="_blank">美胸女王李宓儿胸猛来袭激情自摸大尺度诱惑</a></span><span
                        class="time">3周前 (04/03)</span><span class="view">927,004次</span></li>
                <li><a href="http://www.mzitu.com/61622" target="_blank"><img width="236" height="354" class="lazy"
                                                                              alt="血脉喷张! 绝色香艳女子易阳巨乳摇摇欲坠"
                                                                              src="http://pic.mmfile.net/pfiles/img/lazy.png"
                                                                              data-original="http://pic.mmfile.net/thumbs/2016/04/61622_01b27_236.jpg"></a><span><a
                        href="http://www.mzitu.com/61622" target="_blank">血脉喷张! 绝色香艳女子易阳巨乳摇摇欲坠</a></span><span
                        class="time">3周前 (04/02)</span><span class="view">1,919,240次</span></li>
            </ul>
            <div class="pagenavi"><span class="page-numbers current"><span>1</span></span>
                <a class="page-numbers" href="http://www.mzitu.com/xinggan/page/2"><span>2</span></a>
                <a class="page-numbers" href="http://www.mzitu.com/xinggan/page/3"><span>3</span></a>
                <a class="page-numbers" href="http://www.mzitu.com/xinggan/page/4"><span>4</span></a>
                <span class="page-numbers dots">…</span>
                <a class="page-numbers" href="http://www.mzitu.com/xinggan/page/65"><span>65</span></a>
                <a class="next page-numbers" href="http://www.mzitu.com/xinggan/page/2"><span>下一页»</span></a></div>
            <div id="index_banner" style="height: 90px;"></div>
        </div>
    </div>
    <div class="sidebar">
        <div class="widgets_ad">☞&nbsp;<span>Mzitu.com</span>&nbsp;手机访问更精彩!</div>
        <div class="widgets_hot"><h3>热门专题</h3><span><a href="http://www.mzitu.com/tag/xiuren" target="_blank"
                                                       class="pink">秀人网</a><a href="http://www.mzitu.com/tag/xinggan"
                                                                              target="_blank">性感</a><a
                href="http://www.mzitu.com/tag/youhuo" target="_blank" class="pink">诱惑</a><a
                href="http://www.mzitu.com/tag/zhifu" target="_blank">制服</a><a href="http://www.mzitu.com/tag/shuiyi"
                                                                               target="_blank" class="pink">情趣睡衣</a><a
                href="http://www.mzitu.com/tag/qingchun" target="_blank">清纯</a><a
                href="http://www.mzitu.com/tag/xiaohua" target="_blank">校花</a><a href="http://www.mzitu.com/tag/rosi"
                                                                                 target="_blank" class="pink">ROSI</a><a
                href="http://www.mzitu.com/tag/bikini" target="_blank">比基尼</a><a href="http://www.mzitu.com/tag/leg"
                                                                                 target="_blank">美腿</a><a
                href="http://www.mzitu.com/tag/zouguang" target="_blank" class="pink">走光</a><a
                href="http://www.mzitu.com/tag/meitun" target="_blank">美臀</a><a href="http://www.mzitu.com/tag/tgod"
                                                                                target="_blank">推女神</a><a
                href="http://www.mzitu.com/tag/shishen" target="_blank" class="pink">湿身</a><a
                href="http://www.mzitu.com/tag/heisi" target="_blank">黑丝</a><a href="http://www.mzitu.com/tag/tuigirl"
                                                                               target="_blank">推女郎</a><a
                href="http://www.mzitu.com/tag/ugirls" target="_blank" class="pink">Ugirls</a><a
                href="http://www.mzitu.com/tag/mistar" target="_blank">魅妍社</a><a href="http://www.mzitu.com/tag/bololi"
                                                                                 target="_blank">波萝社</a><a
                href="http://www.mzitu.com/tag/imiss" target="_blank">爱蜜社</a><a href="http://www.mzitu.com/tag/yougou"
                                                                                target="_blank" class="pink">有沟必火</a><a
                href="http://www.mzitu.com/tag/dianannan" target="_blank">嗲囡囡</a></span></div>
        <div class="widgets_top"><h3>性感妹子排行榜</h3>
            <a target="_blank" href="http://www.mzitu.com/52276"><img width="115" height="115"
                                                                      alt="性感少妇SiByl李思宁脱去情趣睡衣全裸入浴"
                                                                      src="http://pic.mmfile.net/thumbs/2015/11/52276_09x34_115.jpg"></a>
            <a target="_blank" href="http://www.mzitu.com/55183"><img width="115" height="115" alt="气质美人姿态万千,推女郎易阳无圣光流出"
                                                                      src="http://pic.mmfile.net/thumbs/2015/12/55183_20k25_115.jpg"></a>
            <a target="_blank" href="http://www.mzitu.com/61622"><img width="115" height="115"
                                                                      alt="血脉喷张! 绝色香艳女子易阳巨乳摇摇欲坠"
                                                                      src="http://pic.mmfile.net/thumbs/2016/04/61622_01b27_115.jpg"></a>
            <a target="_blank" href="http://www.mzitu.com/59402"><img width="115" height="115"
                                                                      alt="性感美女果儿Victoria大尺度写真巨乳遮不住"
                                                                      src="http://pic.mmfile.net/thumbs/2016/02/59402_28v39_115.jpg"></a>
        </div>
        <div class="widgets_fix" id="widgets_fix">
            <div id="sidebar_250_fix" style="height: 250px;">
                <div id="BAIDU_SSP__wrapper_u2506160_0">
                    <iframe id="iframeu2506160_0"
                            src="http://pos.baidu.com/bcrm?rdid=2506160&amp;dc=2&amp;di=u2506160&amp;dri=0&amp;dis=0&amp;dai=1&amp;ps=0x0&amp;dcb=BAIDU_SSP_define&amp;dtm=BAIDU_DUP_SETJSONADSLOT&amp;dvi=0.0&amp;dci=-1&amp;dpt=none&amp;tsr=0&amp;tpr=1461591412811&amp;ti=%E6%80%A7%E6%84%9F%E5%A6%B9%E5%AD%90%20-%20%E5%A6%B9%E5%AD%90%E5%9B%BE&amp;ari=1&amp;dbv=2&amp;drs=1&amp;pcs=1868x901&amp;pss=1868x3683&amp;cfv=18&amp;cpl=37&amp;chi=3&amp;cce=true&amp;cec=UTF-8&amp;tlm=1461585677&amp;ltu=http%3A%2F%2Fwww.mzitu.com%2Fxinggan&amp;ltr=http%3A%2F%2Fwww.mzitu.com%2Fxinggan&amp;ecd=1&amp;psr=1920x1080&amp;par=1920x1030&amp;pis=-1x-1&amp;ccd=24&amp;cja=true&amp;cmi=61&amp;col=zh-CN&amp;cdo=-1&amp;tcn=1461591413&amp;qn=f23ec3c79714778d&amp;tt=1461591412789.25.139.141"
                            width="250" height="250" align="center,center" vspace="0" hspace="0" marginwidth="0"
                            marginheight="0" scrolling="no" frameborder="0"
                            style="border:0; vertical-align:bottom;margin:0;" allowtransparency="true"></iframe>
                </div>
            </div>
            <dl class="widgets_like" id="like">
                <dt><span class="on" id="guess">猜你喜欢</span><span id="love">网友最爱</span></dt>
                <dd><a href="http://www.mzitu.com/51202" target="_blank">宅男女神杉原杏璃比基尼秀浑圆美乳</a></dd>
                <dd><a href="http://www.mzitu.com/5866" target="_blank">黑丝性感内衣床上诱惑</a></dd>
                <dd><a href="http://www.mzitu.com/12304" target="_blank">杉原杏璃性感诱惑</a></dd>
                <dd><a href="http://www.mzitu.com/12113" target="_blank">90妹子自拍系列(3)</a></dd>
                <dd><a href="http://www.mzitu.com/42722" target="_blank">甜美小公主 Beautyleg 美腿写真 No.1142 Aries</a></dd>
                <dd><a href="http://www.mzitu.com/59974" target="_blank">日本性感尤物原干惠比基尼大片 甜美笑容风情万种</a></dd>
                <dd><a href="http://www.mzitu.com/25266" target="_blank">小女子嫩白可爱迷人</a></dd>
                <dd><a href="http://www.mzitu.com/20348" target="_blank">秀人 NO.080 陈思琪 精选 part2</a></dd>
                <dd><a href="http://www.mzitu.com/9482" target="_blank">丰满长腿美女佐藤江梨子高清诱惑</a></dd>
                <dd><a href="http://www.mzitu.com/22506" target="_blank">天使般的唯美</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/48149" target="_blank">美女云集性感争艳 Ugirls尤果网 七夕典藏 Part2</a>
                </dd>
                <dd class="no"><a href="http://www.mzitu.com/20513" target="_blank">闺蜜午后私房写真集</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/40993" target="_blank">Rosi大胸美女红色睡衣诱惑</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/13138" target="_blank">艾子的黑丝长腿</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/21165" target="_blank">秀色可餐 BOMB.tv Miwako Kakei 笕美和子
                    Part2</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/23214" target="_blank">00后清纯美眉微博秀自拍</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/6357" target="_blank">清纯兔女郎</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/30816" target="_blank">情趣黑丝热辣钢管舞 绮里嘉</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/36667" target="_blank">动感小站布布湿身性感比基尼自摸</a></dd>
                <dd class="no"><a href="http://www.mzitu.com/8855" target="_blank">日本美女杉原杏璃</a></dd>
            </dl>
        </div>
    </div>
    <div class="clearfloat"></div>
</div>
<div class="footer">© 2016 <a href="http://www.mzitu.com">妹子图</a> / <a href="http://www.mzitu.com/model">靓丽模特</a> / <a
        href="http://www.mzitu.com/japan">日本美女</a> / <a href="http://www.mzitu.com/taiwan">台湾美眉</a> / <a
        href="http://www.mzitu.com/mm">小清新</a> / <a href="http://www.mzitu.com/all">图片索引</a> / <a
        href="http://www.mzitu.com/feed" target="_blank">RSS订阅</a><br><a href="http://www.mzitu.com"><img alt="妹子图"
                                                                                                          src="http://pic.mmfile.net/pfiles/img/footer.gif"
                                                                                                          width="209"
                                                                                                          height="29"></a>
</div>
<script type="text/javascript" async=""
        src="http://pos.baidu.com/bcrm?di=u2506160&amp;dri=0&amp;dis=0&amp;dai=1&amp;ps=0x0&amp;dcb=BAIDU_SSP_define&amp;dtm=BAIDU_DUP_SETJSONADSLOT&amp;dvi=0.0&amp;dci=-1&amp;dpt=none&amp;tsr=0&amp;tpr=1461591412811&amp;ti=%E6%80%A7%E6%84%9F%E5%A6%B9%E5%AD%90%20-%20%E5%A6%B9%E5%AD%90%E5%9B%BE&amp;ari=1&amp;dbv=2&amp;drs=1&amp;pcs=1868x901&amp;pss=1868x3683&amp;cfv=18&amp;cpl=37&amp;chi=3&amp;cce=true&amp;cec=UTF-8&amp;tlm=1461585677&amp;ltu=http%3A%2F%2Fwww.mzitu.com%2Fxinggan&amp;ltr=http%3A%2F%2Fwww.mzitu.com%2Fxinggan&amp;ecd=1&amp;psr=1920x1080&amp;par=1920x1030&amp;pis=-1x-1&amp;ccd=24&amp;cja=true&amp;cmi=61&amp;col=zh-CN&amp;cdo=-1&amp;tcn=1461591413"></script>
<script type="text/javascript" src="http://pic.mmfile.net/pfiles/index160123.js"></script>
<div class="totop"><a class="cweixin cbbtn" ><span class="weixin-icon"></span>
    <div></div>
    </a><a class="gotop cbbtn"><span class="up-icon"></span></a></div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#index_250_load").find("div").appendTo("#sidebar_250_fix");
        $("#sidebar_250_fix").css("height", 250);
        $("#index_250_load").remove();
    });
</script>
<script type="text/javascript">
    var cpro_psid = "u2095296";
</script>
<script src="http://su.bdimg.com/static/dspui/js/f.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $("#index_banner_load").find("div").appendTo("#index_banner");
        $("#index_banner").css("height", 90);
        $("#index_banner_load").remove();
    });
</script>

<div class="sogoutip" style="z-index: 2147483645; visibility: hidden;"></div>
<div class="sogoubottom" id="sougou_bottom" style="display: block;"></div>
<div id="ext_stophi" style="z-index: 2147483647;">
    <div class="extnoticebg"></div>
    <div class="extnotice"><h2>关闭提示 <a href="#" title="关闭提示" id="closenotice" class="closenotice">关闭</a></h2>
        <p id="sogouconfirmtxt"></p>  <a id="sogouconfirm" href="#" class="extconfirm">确 认</a> <a id="sogoucancel"
                                                                                                  href="#"
                                                                                                  class="extconfirm">取
            消</a></div>
</div>
<div id="ext_overlay" class="ext_overlayBG" style="display: none; z-index: 2147483646;"></div>
<iframe class="sogou_sugg_feedbackquan" frameborder="0" scrolling="no"
        src="http://ht.www.sogou.com/websearch/features/yun4.jsp?pid=sogou-brse-596dedf4498e258e&amp;w=1920&amp;v=7212&amp;st=1461591412842&amp;od=4681&amp;ls=1461590354579&amp;lc=&amp;lk=1458830415341&amp;sd=48&amp;cd=0&amp;kd=0&amp;u=1450153871192223&amp;y=E746155D28B90A0ACCC2FA8EB2CCB4A1&amp;query=%E6%80%A7%E6%84%9F%E5%A6%B9%E5%AD%90%20-%20%E5%A6%B9%E5%AD%90%E5%9B%BE|http%3A%2F%2Fwww.mzitu.com%2Fxinggan&amp;r=http%3A%2F%2Fwww.mzitu.com%2F63289"
        style="border: none; display: block; z-index: 2147483645; background: transparent;"></iframe>
<div class="rec-mini" id="id_div_close"
     style="display: none; z-index: 2147483645; width: 53px; height: 53px; position: fixed; right: 7px; bottom: 7px; cursor: pointer; background: url(http://ht.www.sogou.com/images/mini.png) no-repeat;"></div>
<script src="http://pb.sogou.com/pv.gif?hintbl=-1&amp;uigs_productid=webext&amp;type=ext_sugg&amp;uigs_t=1461591413839&amp;lt=230&amp;ie=0&amp;v=7212&amp;y=E746155D28B90A0ACCC2FA8EB2CCB4A1&amp;query=%E6%80%A7%E6%84%9F%E5%A6%B9%E5%AD%90%20-%20%E5%A6%B9%E5%AD%90%E5%9B%BE|http%3A%2F%2Fwww.mzitu.com%2Fxinggan"></script>
</body>
</html>"""
print('start')
soup = BeautifulSoup(html_doc, "html.parser", from_encoding="utf-8")
print(soup.original_encoding)
soup.contains_replacement_characters = True
# def isMenutag(tag):
#     if tag==None:
#         return False
#     else:
#         return tag.has_attr('class') and tag.get_attr('class') == 'menu'
# tag_menu = soup.find_all('ul', 'menu')
# for li in tag_menu.pop().find_all('li'):
#     print(li.find('a').get('href'))
#     print(li.find('a').string)
print(soup.select('ul > li > a'))
# for child in tag_menu:
#     meizi = BeautifulSoup(child, "html.parser")
#     print("start")
#     print(meizi.children)
# html_doc = """
# <html><head><title>The Dormouse's story</title></head>
#
# <p class="title"><b>The Dormouse's story</b></p>
#
# <p class="story">Once upon a time there were three little sisters; and their names were
# <a href="http://example.com/elsie" class="sister" id="link1">Elsie</a>,
# <a href="http://example.com/lacie" class="sister" id="link2">Lacie</a> and
# <a href="http://example.com/tillie" class="sister" id="link3">Tillie</a>;
# and they lived at the bottom of a well.</p>
#
# <p class="story">...</p>
# """
# soup = BeautifulSoup(html_doc,"html.parser")
# print(soup.head.title)
