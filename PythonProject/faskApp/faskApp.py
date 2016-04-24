# -*- coding: utf-8 -*-
from flask import Flask,url_for,render_template

app = Flask(__name__)


@app.route('/,',methods='get')
def hello_world():
    return render_template('index.html',user="zxw")


@app.route('/user/<id>')
def user_id(id):
    return 'hello user:'+id

# 反向路由，返回函数对应的路由地址
@app.route('/query_url')
def query_url():
    return 'query url'+url_for(query_url)
if __name__ == '__main__':
    app.run()
