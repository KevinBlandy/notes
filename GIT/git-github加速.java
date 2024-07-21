-----------------
配置全局加速地址
-----------------
	# 全局配置
		git config --global url."https://gitclone.com/".insteadOf https://
	
	#取消
		git config --global --unset url."https://gitclone.com/".insteadOf
	
	# 查看配置
		git config --list

-----------------
也可以尝试切换协议
-----------------

	git config --global url."https://".insteadOf git://
