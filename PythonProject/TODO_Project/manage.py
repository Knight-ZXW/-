from app import app
from app.models import ToDo
from flask.ext.script import Manager

manager = Manager(app)

@manager.command
def save():
    todo = ToDo(content = "study flask")
    todo.save()

if __name__ == '__main__':
    manager.run()