
OUT FILE
	1,导出文本数据
		* 所谓的OUT FILE,其实就是把查询的结果保存到磁盘文件,不发送到客户端
		* 示例
			SELECT * INTO outfile 'D:/CACHE/test.txt' FROM _user;
			//在文件中,默认每条记录占一行
		* 需要注意的是,该SQL语句并不能创建文件夹,只能创建文件.所以,指定路径下的文件夹必须存在
		* 而且,文件不能重名.当目录下已经存在了同名文件,无法写入,SQL异常
		* 还支持其他的数据显示方式(就是查询结果显示在文件中的样式)

	2,导出二进制数据
		* 这种导出方式的数据,不做任何的转换和转义处理,非常适合作为二进制数据的导出
		* 示例
			SELECT user.pic INTO dumpfile 'D:/CACHE/pic.jpg' FROM _user AS user WHERE name='Kevin';
		* 同样的,不能有重名文件,且无法创建文件夹
		