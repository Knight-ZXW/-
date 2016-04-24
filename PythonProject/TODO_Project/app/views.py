from app import app
from flask import render_template, request
from .models import Todo,TodoForm


# 这是装饰器模式的应用，注解的形式等同于app.add_url_rule('/')
@app.route('/')
def index():
    form = TodoForm
    todos = Todo.objects.order_by('-time')
    return render_template('index.html', todos=todos, form=form)

