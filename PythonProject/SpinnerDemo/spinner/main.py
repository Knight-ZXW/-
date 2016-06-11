import json
from urllib import request, error

from bs4 import BeautifulSoup, Tag

from bean.MeiZiClass import *


# xinggan japan
# http://www.mzitu.com/japan/page/2
def get_html_data(url):
    req = request.Request(url)
    # sougouAgent = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0'
    req.add_header('User-Agent',
                   'Mozilla/6.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/8.0 Mobile/10A5376e Safari/8536.25')
    # req.add_header('User-Agent',sougouAgent)
    # try:
    with request.urlopen(req) as f:
        data = f.read()
        print(f.status)
        if (f.status == 200):
            for k, v in f.getheaders():
                print('%s: %s' % (k, v))
            return data.decode('utf-8')
            # except error as e:
            #     if hasattr(e, "code"):
            #         print(e.code)
            #     if hasattr(e, "reason"):
            #         print(e.reason)


from utils import utils
import os
import re

if __name__ == '__main__':
    html_str = get_html_data('http://www.mzitu.com/')
    soup = BeautifulSoup(html_str, "html.parser")

    tag_menus = soup.find(class_='menu')
    meiziClassList = list()
    for tag_menu in tag_menus:
        if (type(tag_menu) == Tag):
            print(tag_menu.a['href'], tag_menu.a['title'])
            meiziClassList.append(meiziClass(tag_menu.a['title'], tag_menu.a['href']))

            # html_xinggan_str = get_html_data('http://www.mzitu.com/xinggan')
            # soup_xinggan = BeautifulSoup(html_xinggan_str, 'html.parser')
            # # print(soup_xinggan)
            # tag_postlist = soup_xinggan.find(class_='placeholder')
            # print(tag_postlist.prettify())
            # #     创建性感文件夹
            # total_post_re = re.compile(r'".*.com/":(\s+),')
            # print(total_post_re.findall('http://www.mzitu.com/xinggan')[0])
            # # utils.check_exist_path(os.getcwd(),)
