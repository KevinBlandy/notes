
-------------------------------------
centos7长时间不操作导致连接超时中断  |
-------------------------------------

1.编辑sshd配置文件

    # vi /etc/ssh/sshd_config

    找到

    #ClientAliveInterval 0
    #ClientAliveCountMax 3

    修改为

    ClientAliveInterval 60
    ClientAliveCountMax 3

2.重启sshd服务

    # systemctl restart sshd
