import json
class meiziClass():
    def __init__(self,title,url):
        self.title = title
        self.url = url
        self.urls = list()

    def json_str(self):
        return

class meizi_Pics:
    def __init__(self,title):
        self.title = title
        self.img_urls = list()
    def add_Pic(self,pic):
        self.img_urls.append(pic)

    def __str__(self):
        'total'+str(len(self.img_urls))
class meizi_Pic:
    def __init__(self,title,url,width,height):
        self.title = title
        self.url = url
        self.width = width
        self.height = height

    def __str__(self):
        'meizi:'+self.url