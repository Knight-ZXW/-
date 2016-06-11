#!/usr/bin/env python
# -*-coding:utf-8-*-


import sys
import os
import requests


def download_imgs(url, path, name, proxies=None):
    try:
        r = requests.get(url, stream=True, proxies=proxies, timeout=10)
        file = os.path.join(path, name)
        with open(file, 'wb') as f:
            for chunk in r.iter_content(chunk_size=1024):
                f.write(chunk)
    except Exception as e:
        print("下载失败, %s %s" % (url, e))

def check_exist_path(path):
    if not os.path.isdir(path):
        os.makedirs(path)

if __name__ == '__main__':
    path = os.path.join(os.getcwd(), "imgs/", 'dir1')
    check_exist_path(path)
    download_imgs('http://36.media.tumblr.com/00f15d63f0d023f9b164d5bd37b6ee40/tumblr_o5h1ihmCcR1qcrhtio1_1280.jpg',path,name='test.jpg',proxies=None)