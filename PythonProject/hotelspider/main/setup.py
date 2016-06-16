from distutils.core import setup
import sys
import py2exe

#this allows to run it with a simple double click
sys.argv.append('py2exe')

py2exe_options = {
    "dll_excludes":["MSCVP90.dll",],
    "compressed": 1,
        "optimize": 2,
        "ascii": 0,
        "bundle_files": 1,  # 关于这个参数请看第三部分中的问题(2)
        }

setup(
      name = 'Hotel spide',
      version = '1.0',
      console = ['client.py',],   # 括号中更改为你要打包的代码文件名
      zipfile = None,
      options = {'py2exe': py2exe_options}
      )