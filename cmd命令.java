
# 获取文件的hash值
	certutil -hashfile [文件] [hash算法]

	* hash算法可以是: md5,sha1,sha256

# 清除由于网络问题带来的,maven依赖下载失败遗留的 .lastupdate/_remote.repositories文件
	for /r %i in (*.lastUpdated)do del %i
	for /r %i in (*.repositories)do del %i

	for /r %i in (*.lastUpdated)do del %i & for /r %i in (*.repositories)do del %i

	* 需要在maven的仓库目录执行

# 使用 pause 指令让控制台 "请按任意键继续。。。"

# 修改cmd的字符集
	CHCP
		* 查看当前的编码默认为GBK(936)
	CHCP 65001
		* 设置编码为utf-8
		* 设置成功后如果不能正常显示,则尝试设置一下cmd属性里面的字体

# 查看端口占用进程

	netstat -aon|findstr "8081"


# 启动 Java 应用，输出 PID

	nohup /usr/local/jdk-21.0.5/bin/java -jar app.jar --spring.profiles.active=test --server.port=8080 > /dev/null 2>&1 & echo $! > app.pid &


	// 多个参数，建议换行
	nohup java -jar app.jar \
		--spring.profiles.active=test \
		--server.port=8080 \
		--server.servlet.context-path=/api \
		--spring.datasource.url=jdbc:mysql://localhost:3306/db \
		--spring.datasource.username=root \
		--spring.datasource.password=123456 \
		--logging.level.root=INFO \
		> /dev/null 2>&1 & echo $! > app.pid
