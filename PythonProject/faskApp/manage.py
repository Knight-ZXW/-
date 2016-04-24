from flask_script import Manager
from faskApp import app
import sqlite3
from models import  User

manager = Manager(app)


@manager.command
def hello():
    print ("hello world")


@manager.option('-m','--msg',dest = 'msg_val',default ='world')
def hello_world(msg_val):
    print ('hello'+ msg_val)


@manager.command
def init_db():
    sql = 'create table user (id INT, name TEXT)'
    conn = sqlite3.connect('test.db')
    cursor = conn.cursor()
    cursor.execute(sql)
    conn.commit()
    cursor.close()
    conn.close()


@manager.command
def save():
    user = User(1,'jike')
    user.save()

@manager.command
def query_all():
    users = User.query()
    for user in users:
        print(user)

if __name__ == "__main__":
    manager.run()