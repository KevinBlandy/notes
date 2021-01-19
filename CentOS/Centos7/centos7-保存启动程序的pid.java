--------------------
保存启动程序的pid	|
--------------------
	# nohup java [command] & echo $! > [file].pid

		command 执行启动的命令
		file 保存的pid文件

	# demo
		nohup java -jar socket.jar > socket.log 2 >&1 & echo $! > socket.pid