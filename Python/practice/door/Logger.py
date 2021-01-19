
import logging
import sys
import os

logger = None

log_file = sys.path[-1] + os.sep +'door.log'

def getLogger():

    global logger

    if not logger :

        logger = logging.getLogger('root')
        logger.setLevel(logging.DEBUG)

        streamHandler = logging.StreamHandler()
        fileHandler = logging.FileHandler(log_file, encoding='UTF_8')

        formatter = logging.Formatter(
            # [时间][文件-行号][LEVEL]:日志正文       [%(asctime)s][%(filename)s-%(lineno)s][%(levelname)s]:%(message)s
            fmt="[%(asctime)s] %(message)s",
            datefmt="%Y-%m-%d %H:%M:%S")

        streamHandler.setFormatter(formatter)
        fileHandler.setFormatter(formatter)

        logger.addHandler(streamHandler)
        logger.addHandler(fileHandler)

    return logger

