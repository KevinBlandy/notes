---------------------------
tarfile						|
---------------------------
	* tar包处理


---------------------------
tarfile-demo				|
---------------------------
import tarfile
 
# 压缩
tar = tarfile.open('your.tar','w')
tar.add('/Users/wupeiqi/PycharmProjects/bbs2.log', arcname='bbs2.log')
tar.add('/Users/wupeiqi/PycharmProjects/cmdb.log', arcname='cmdb.log')
tar.close()
 
# 解压
tar = tarfile.open('your.tar','r')
tar.extractall()  # 可设置解压地址,关键字参数:path ,默认当前目录
tar.close()