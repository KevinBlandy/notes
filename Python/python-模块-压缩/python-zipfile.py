-----------------------
zipfile					|
-----------------------
	* zip压缩包处理


-----------------------
zipfile-demo			|
-----------------------
import zipfile
# 压缩
z = zipfile.ZipFile('laxi.zip', 'w')
z.write('a.log')
z.write('data.data')
z.close()
# 解压
z = zipfile.ZipFile('laxi.zip', 'r')
z.extractall()	# 可设置解压地址,关键字参数:path
z.close()

