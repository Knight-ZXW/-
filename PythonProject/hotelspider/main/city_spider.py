import os
from threading import Thread
from concurrent.futures import ThreadPoolExecutor
from bs4 import BeautifulSoup
import re
import json, simplejson

def spidecityname():
    names = [ ('html/'+name) for name in os.listdir('./html/') if name.endswith('html')]
    pool = ThreadPoolExecutor(5)
    hotel_dict = dict()
    for name in names:
        s_dict = loadHtmlbackDict(name,hotel_dict)
        # hotel_dict = dict(hotel_dict,**s_dict)

    with open('city.txt','a+',encoding='utf-8') as f:
        f.write(simplejson.dumps(hotel_dict,ensure_ascii=False))

def loadHtmlbackDict(filename,map_src):
    file_object = open(filename, 'r', encoding='utf-8')
    hotel_name_dict = dict()
    html_doc = file_object.read()
    with open(filename, 'r', encoding='utf-8') as f:
            soup = BeautifulSoup(f.read(), "html.parser")
            # re.compile("t")
            tag_wrapped_hotels = soup.select('div#brand_list')[0].find_all('dl', class_='clearfix')
            for tag_wrapped_hotel in tag_wrapped_hotels:
                tag_a = tag_wrapped_hotel.find('div', class_='book').find('a')
                hotel_name_py = re.findall(r'\(\'(\w+)\'\)', str(tag_a))[0]
                hotel_name_cn = tag_wrapped_hotel.find('h3').find('span').text
                hotel_name_dict[hotel_name_py] = hotel_name_cn
                print(hotel_name_cn)
    map_src = dict(map_src,**hotel_name_dict)
    return map_src

if __name__ == '__main__':
    spidecityname()