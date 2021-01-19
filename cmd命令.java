
# 获取文件的hash值
	certutil -hashfile [文件] [hash算法]

	* hash算法可以是: md5,sha1,sha256

# 清除由于网络问题带来的,maven依赖下载失败遗留的 .lastupdate文件
	for /r %i in (*.lastUpdated)do del %i

	* 需要在maven的仓库目录执行

# 使用 pause 指令让控制台 "请按任意键继续。。。"

# 修改cmd的字符集
	CHCP
		* 查看当前的编码默认为GBK(936)
	CHCP 65001
		* 设置编码为utf-8
		* 设置成功后如果不能正常显示,则尝试设置一下cmd属性里面的字体
