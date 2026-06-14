-----------
rsync
-----------
    # rsync
        https://github.com/RsyncProject/rsync
    

    # 基本用法

        rsync -avz root@192.168.1.1:/data/ /data/

        * 第一个路径就是源文件/目录，如果不以 / 结尾的话，就会在目标目录下创建
        * 指定端口 -e "ssh -p 22"
        * 断点续传 -P
        * 限速 --bwlimit=50m

