# coding=utf-8

import requests
import json,time
from bs4 import BeautifulSoup
import threading



with open('city.txt', encoding='utf-8') as city_file:
    json_str = city_file.read()
    map_citys = json.loads(json_str)

def spiderhotel(cityPy,startTime,endTime):

    url = r'http://www.kaiyuanhotels.com.cn/web/rate/roomRate.htm?source=WEB&t=1465977163526&corprateStatus=0&hotelCode={0}&arrDateStr={1}&depDateStr={2}'.format(cityPy,startTime,endTime)
    res =""
    try:
        response = requests.get(url)
        if(response.status_code == 200):
            html_doc = response.content
            soup = BeautifulSoup(html_doc, "html.parser")
            hotel_each_list = soup.find_all("div", class_="roomEach")
            print(len(hotel_each_list))
            for Tag_eachHouse in hotel_each_list:
                try:
                    house_type_str = Tag_eachHouse.find('div', class_='info').find('p').text
                    # print(house_type_str)
                    Tag_PriceTypes = Tag_eachHouse.find_all('div', class_='priceEach clearfix')
                    # print(len(Tag_PriceTypes))
                    for Tag_PlanType in Tag_PriceTypes:
                        house_plan_name = Tag_PlanType.find('div', class_='width_2').text.split()[0]
                        tag_a_isfull = Tag_PlanType.find('div', class_='width_6').strong
                        if tag_a_isfull.text == '满房':
                            single_res = '    {0}-{1}-{2}'.format(house_type_str, house_plan_name, '满房了\n')
                            print(single_res)
                            res+=single_res
                except BaseException as e:
                    print('exception' + e.__str__())
        else:
            print('爬取{0},网络状态状态{1}:'.format(url,response.status_code))
    except requests.HTTPError as  http_error:
        print(http_error.__str__())
    except BaseException as e:
        print('exceptoin'+e.__str__())
    finally:
        return res

def spiderhotel2(cityPy,startTime,endTime,file):
    url = r'http://www.kaiyuanhotels.com.cn/web/rate/roomRate.htm?source=WEB&t=1465977163526&corprateStatus=0&hotelCode={0}&arrDateStr={1}&depDateStr={2}'.format(
        cityPy, startTime, endTime)
    res = ""
    try:
        print('连接{}中'.format(map_citys[cityPy]))
        response = requests.get(url)
        if (response.status_code == 200):
            html_doc = response.content
            soup = BeautifulSoup(html_doc, "html.parser")
            hotel_each_list = soup.find_all("div", class_="roomEach")
            # f.write(time.strftime("%Y-%m-%d %H:%M:%S", ) + ':爬取: ' + str(map_citys[k]))
            res+=time.strftime("%Y-%m-%d %H:%M:%S", ) + ':爬取: ' + str(map_citys[k])
            for Tag_eachHouse in hotel_each_list:
                try:
                    house_type_str = Tag_eachHouse.find('div', class_='info').find('p').text
                    Tag_PriceTypes = Tag_eachHouse.find_all('div', class_='priceEach clearfix')
                    for Tag_PlanType in Tag_PriceTypes:
                        house_plan_name = Tag_PlanType.find('div', class_='width_2').text.split()[0]
                        tag_a_isfull = Tag_PlanType.find('div', class_='width_6').strong
                        if tag_a_isfull.text == '满房':
                            single_res = '    {0}-{1}-{2}'.format(house_type_str, house_plan_name, '满房了\n')
                            res += single_res

                except BaseException as e:
                    print('exception' + e.__str__())
        else:
            print('爬取{0},网络状态状态{1}:'.format(url, response.status_code))
    except requests.HTTPError as  http_error:
        print(http_error.__str__())
    except BaseException as e:
        print('exceptoin' + e.__str__())
    finally:
        file.write(res)
        file.write('\n---------------------------------\n')
        return res

def printMainMenu():
    print('##################  ' + time.strftime("%Y-%m-%d %H:%M:%S", ) + '  ##################\n')
    print('##################     菜单(不区分大小写)   ##################\n')
    print('########    1. 输入A 查询全部 ##############\n')
    print('########    2. 输入酒店对应简写字母 查询相应酒店 ##############\n')
    print('########    3. 输入C 清空 文本 ##############\n')
    print('########    4. 输入Q 退出 ##############\n')
    print('########    5. 输入all查看全部酒店简写 ##############\n')


def time_now_add_day(day_add=0):
    day = time.strftime("%Y-%m-%d",time.localtime(time.time()+60*60*24*day_add))
    print('您输入{0}'.format(day))
    return day

def print_input_start_time():
    print('请输入起始时间至今的天数,如 0 表示今天（默认为今天，如需默认，直接回车）')
    increaseday = input()

    if increaseday.isdigit() and int(increaseday) >=0:
        return time_now_add_day(day_add=int(increaseday))
    else:
        print('默认当天或者非法输入（自动选取当天）')
        return time_now_add_day(day_add=int(0))

def print_input_end_time():
    print('请输入截止时间至今的天数,如 1 表示明天（默认为明天，如需默认，直接回车），')
    increaseday = input()
    if increaseday.isdigit():
        return time_now_add_day(day_add=int(increaseday))
    else:
        print('默认明天或者非法输入（自动选取明天）')
        return time_now_add_day(day_add=int(1))

import os
def check_exist_path(path):
    if not os.path.isdir(path):
        os.makedirs(path)

if __name__ == '__main__':
    res  =''
    res+=str(time.time())+'\n'

    while(True):
        printMainMenu()
        choose_menu = input()
        with open('city.txt',encoding='utf-8') as city_file:
            with open('result'+time.strftime("%Y-%m-%d", )+'.txt', mode='a+', encoding='utf-8') as f:
                print(time.strftime("%Y-%m-%d %H:%M:%S",))
                # print(city_file.read())
                json_str = city_file.read()
                map_citys = json.loads(json_str)
                try:
                    if(choose_menu.upper()=='A'):
                        start_day = print_input_start_time()
                        end_day = print_input_end_time()
                        total = len(map_citys)
                        for k in map_citys:
                            print('正在爬取中：...')
                            print('爬取{} '.format(map_citys[k]))
                            f.write(str(time.strftime("%Y-%m-%d %H:%M:%S", ))+'\n:爬取: '+str(map_citys[k])+'\n')
                            f.write('起止{0},截止{1}'.format(start_day,end_day))
                            single_res = spiderhotel(k,start_day,end_day)
                            print(single_res)
                            print('分析完成:{0}/{1}'.format(k,total))
                            f.write(single_res)
                            print('写入文件'+f.name+'完成')
                            f.write('\n---------------------------------\n')
                    elif(map_citys.get(choose_menu.upper(),None)):
                            start_day = print_input_start_time()
                            end_day = print_input_end_time()
                            print('正在爬取中：...')
                            print('爬取{} '.format(map_citys.get(k,None)))
                            f.write(str(time.strftime("%Y-%m-%d %H:%M:%S", ))+'\n:爬取: '+str(map_citys[k])+'\n')
                            f.write('起止{0},截止{1}'.format(start_day,end_day))
                            single_res = spiderhotel(k,start_day,end_day)
                            print(single_res)
                            print('分析完成:')
                            f.write(single_res)
                            f.write('\n---------------------------------\n')
                            print('写入文件'+f.name+'完成')
                    elif(choose_menu.upper() == 'Q'):
                        print('直接看city.txt')
                        break
                    elif(choose_menu.upper() == 'C'):
                        print('清空中...')
                        f.truncate()
                        print('清空完成')
                    elif(choose_menu.upper() == 'ALL'):
                        print(json_str)
                    else:
                        print('非法输入，请重新输入')
                except BaseException as e:
                    print('异常:'+e.__str__())






