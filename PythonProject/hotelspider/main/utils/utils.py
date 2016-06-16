import os
@staticmethod
def check_exist_path(path):
    if not os.path.isdir(path):
        os.makedirs(path)