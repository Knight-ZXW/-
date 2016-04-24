from flask import Flask,url_for
from flask_mongoengine import  MongoEngine
#如果是单一模块的应用直接使用__name__，因为名称将会不同，__name__对应于实际导入的名称。
# 否则最好使用包名，它会查找资源，传入正确的包名或者模块名称，这样它才能正确的查找资源
app = Flask(__name__)
app.config.from_object('config')

db = MongoEngine(app)
# 为什么这个需要再app之下
from app import models,views

