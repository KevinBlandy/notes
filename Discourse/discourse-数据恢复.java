---------------------------
使用命令行方式进行恢复
---------------------------
	* 恢复前需要的准备工作
		1. 在新服务器上安装完整和全新的 Discourse，这个安装成功后不需要通过 UI 前台进行用户注册。
		2. 备份和获得备份文件。
		3. 确定你现在运行的 Discourse 版本是最新的版本，如果你的 Discourse 不是最新的版本，你需要先进行升级。
	

	1. 把备份文件上传到服务器指定目录
		* 目录
			/var/discourse/shared/standalone/backups/default
		
		* 文件名称
			sitename-2019-02-03-042252-v20190130013015.tar.gz
	
	2. 进入容器
		./launcher enter app
	
		
	3. 开启数据恢复
		discourse enable_restore
	
	4. 执行恢复
		discourse restore sitename-2019-02-03-042252-v20190130013015.tar.gz
	
		* 数据恢复成功的话，你将会在界面的下面看到 [Success] 的提示。
		* 否则将会提示失败，在失败的上面将会提示你失败的原因，你可以根据失败的原因重新调整参数后再重试，很多时候主要是附件的原因。
	
	5. 退出容器 & 重构
		exit
		./launcher rebuild app
	



