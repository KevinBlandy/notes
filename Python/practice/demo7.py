import os
import time
import shutil
path = "F:\\"
while True:
    if os.path.exists(path):
        print("U盘插入,开始copy")
        shutil.copytree(os.path.abspath(path), "e:\\copy")
        print("copy完毕,程序结束")
        break
    time.sleep(3)