---------------------
开放 root 密码登陆
---------------------
    * 修改root账户密码

        sudo -i
        passwd

    * 编辑文件 /etc/ssh/sshd_config
    
        PermitRootLogin yes
        PasswordAuthentication yes
    
    * 重启服务
        systemctl restart ssh

