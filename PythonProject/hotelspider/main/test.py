# import time
# day = time.localtime()
# day_add_1 = day[2]+1
# a = input()
# print(' :'+str(a))
# print(type(a))
# print(input().isdigit())
# print("".isdigit())
# print(type(day[2]))
# # day[2] =day[2]+1
# print(time.strftime("%Y-%m-%d",time.localtime(time.time()+60*60*24)))
import time
res = time.strftime("%Y-%m-%d",time.localtime(time.time()+60*60*24*1))
print(res)