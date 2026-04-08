---------------------
开放 root 密码登陆
---------------------
    * 修改root账户密码

        sudo -i
        passwd

    * 编辑文件 /etc/ssh/sshd_config
    
        PermitRootLogin yes
           * prohibit-password

        PasswordAuthentication yes
    
    * 如果是亚马逊云的 Ubuntu 系统
        * /etc/ssh/sshd_config.d 目录下会有一个 *-settings.conf 文件
        * 编辑这个文件 内容改为 PasswordAuthentication yes
    
    * 重启服务
        systemctl restart ssh

