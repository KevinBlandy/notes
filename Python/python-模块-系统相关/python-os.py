-----------------------
os						|
-----------------------
	* 操作系统相关的模块
	* 关于文件/文件夹操作的一些特性
		1,如果参数非绝对路径,则会根据当前路径进行操作:getcwd() 
		2,参数还支持 . 和 ..  来相对定位到目录


-----------------------
内置属性				|
-----------------------
	str curdir  
		* 当前目录:('.'),字符串
	
	str pardir
		* 当前目录的父目录字符串名:('..')
	
	str sep	
		* 输出操作系统特定的路径分隔符,win下为"\\",Linux下为"/"

	str linesep   
		* 输出当前平台使用的行终止符(换行),win下为"\t\n",Linux下为"\n"

	str pathsep    
		* 分隔符
		* windows 中的 path 环境变量中的分隔符是: ;
		* Linux 中的  path 环境变量中的分隔符是: :
	
	str name    
		* 输出字符串指示当前使用平台,win->'nt'  Linux->'posix'

	os._Environ environ  
		* 获取系统环境变量
		* environ({'ALLUSERSPROFILE': 'C:\\ProgramData', 'APPDATA': 'C:\\Users\\Kevin\\AppData\\Roaming', 'CLASSPATH': '.;C:\\Program Files\\Java\\jdk1.8.0_111\\lib\\dt.jar;C:\\Program Files\\Java\\jdk1.8.0_111\\lib\\tools.jar;', 'COMMONPROGRAMFILES': 'C:\\Program Files (x86)\\Common Files', 'COMMONPROGRAMFILES(X86)': 'C:\\Program Files (x86)\\Common Files', 'COMMONPROGRAMW6432': 'C:\\Program Files\\Common Files', 'COMPUTERNAME': 'DESKTOP-UHNU5C4', 'COMSPEC': 'C:\\WINDOWS\\system32\\cmd.exe', 'ERLANG_HOME': 'C:\\Program Files\\erl8.1', 'GRAILS_HOME': 'C:\\grails-3.1.8', 'GROOVY_HOME': 'C:\\groovy-2.4.7', 'HOMEDRIVE': 'C:', 'HOMEPATH': '\\Users\\Kevin', 'JAVA_HOME': 'C:\\Program Files\\Java\\jdk1.8.0_111', 'LOCALAPPDATA': 'C:\\Users\\Kevin\\AppData\\Local', 'LOGONSERVER': '\\\\DESKTOP-UHNU5C4', 'MAVEN_HOME': 'C:\\apache-maven-3.3.9', 'MOZ_PLUGIN_PATH': 'C:\\Program Files (x86)\\Foxit Software\\Foxit Reader\\plugins\\', 'NUMBER_OF_PROCESSORS': '4', 'OS': 'Windows_NT', 'PATH': 'C:/Program Files/Java/jre1.8.0_111/bin/server;C:/Program Files/Java/jre1.8.0_111/bin;C:/Program Files/Java/jre1.8.0_111/lib/amd64;C:\\ProgramData\\Oracle\\Java\\javapath;C:\\Program Files\\Java\\jdk1.8.0_111\\bin;C:\\Program Files\\Java\\jdk1.8.0_111\\jre\\bin;C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin;C:\\apache-maven-3.3.9\\bin;C:\\WINDOWS\\system32;C:\\WINDOWS;C:\\WINDOWS\\System32\\Wbem;C:\\WINDOWS\\System32\\WindowsPowerShell\\v1.0\\;C:\\grails-3.1.8/bin;C:\\groovy-2.4.7/bin;C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\Scripts\\;C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\;C:\\Users\\Kevin\\bin\\Sencha\\Cmd;C:\\Program Files (x86)\\SSH Communications Security\\SSH Secure Shell;C:\\Users\\Kevin\\AppData\\Local\\Microsoft\\WindowsApps;;E:\\eclipse;', 'PATHEXT': '.COM;.EXE;.BAT;.CMD;.VBS;.VBE;.JS;.JSE;.WSF;.WSH;.MSC', 'PROCESSOR_ARCHITECTURE': 'x86', 'PROCESSOR_ARCHITEW6432': 'AMD64', 'PROCESSOR_IDENTIFIER': 'Intel64 Family 6 Model 55 Stepping 8, GenuineIntel', 'PROCESSOR_LEVEL': '6', 'PROCESSOR_REVISION': '3708', 'PROGRAMDATA': 'C:\\ProgramData', 'PROGRAMFILES': 'C:\\Program Files (x86)', 'PROGRAMFILES(X86)': 'C:\\Program Files (x86)', 'PROGRAMW6432': 'C:\\Program Files', 'PROMPT': '$P$G', 'PSMODULEPATH': 'C:\\WINDOWS\\system32\\WindowsPowerShell\\v1.0\\Modules\\', 'PUBLIC': 'C:\\Users\\Public', 'PYDEV_COMPLETER_PYTHONPATH': 'E:\\eclipse\\plugins\\org.python.pydev_5.7.0.201704111357\\pysrc', 'PYDEV_CONSOLE_ENCODING': 'UTF-8', 'PYTHONIOENCODING': 'UTF-8', 'PYTHONPATH': 'E:\\eclipse\\plugins\\org.python.pydev_5.7.0.201704111357\\pysrc\\pydev_sitecustomize;E:\\workspace\\kevinblandy-python-demo;C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\DLLs;C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\lib;C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32;C:\\Users\\Kevin\\AppData\\Local\\Programs\\Python\\Python36-32\\lib\\site-packages', 'PYTHONUNBUFFERED': '1', 'SESSIONNAME': 'Console', 'SYSTEMDRIVE': 'C:', 'SYSTEMROOT': 'C:\\WINDOWS', 'TEMP': 'C:\\Users\\Kevin\\AppData\\Local\\Temp', 'TMP': 'C:\\Users\\Kevin\\AppData\\Local\\Temp', 'USERDOMAIN': 'DESKTOP-UHNU5C4', 'USERDOMAIN_ROAMINGPROFILE': 'DESKTOP-UHNU5C4', 'USERNAME': 'Kevin', 'USERPROFILE': 'C:\\Users\\Kevin', 'WINDIR': 'C:\\WINDOWS'})

-----------------------
内置函数				|
-----------------------
	str getcwd() 
		* 获取当前工作目录,即当前python脚本所在目录的绝对路径
		* E:\workspace\kevinblandy-python-demo

	None chdir(dirname)  
		* 改变当前脚本工作目录:相当于shell下cd
	
	# 创建==================
	None mkdir(dirname)    
		* 生成单级目录:相当于shell中mkdir dirname
		* 如果文件夹已经存在,抛出异常:FileExistsError
		* 如果参数非绝对路径,则会根据当前路径进行操作:getcwd() ()

	None makedirs(dirnames)    
		* 可以递推的创建多层文件夹
		* 如果文件夹都存在,会抛出异常:FileExistsError
	
	# 删除==================
	None rmdir(dirname)		
		* 删除单级空目录,若目录不为空则抛出异常
		* 相当于shell中rmdir dirname

	None removedirs(dirnames)    
		* 删除多级目录,若目录为空,则删除,并递归到上一级目录,如若也为空,则删除,依此类推
		* 任意目录不为空,抛出异常:OSError
		* 该删除操作是原子性的
	
	None remove(file)  
		* 删除一个文件
	
	# 判断
	bool access(path,model)
		* 用来对目标文件/文件夹进行检测
		* path 要用来检测是否有访问权限的路径
		* model 检测的模式
			os.F_OK:	测试path是否存在
			os.R_OK:	测试path是否可读
			os.W_OK:	测试path是否可写
			os.X_OK:	包含在access()的mode参数中 ，测试path是否可执行
				
				
	# 列出==================
	list listdir(dir)    
		* 列出指定目录下的所有文件和子目录,包括隐藏文件
		* 返回列表
	generator walk(path)
		* 递归指定的目录,返回一个迭代器
		* 该迭代器,每一个元素,是一个 tuple
		* tuple[0]:当前目录
		* tuple[1]:list,当前目录中的子目录(相对路径)
		* tuple[2]:list,当前目录中的文件(相对路径)
		* demo
			for root,dirs,files in os.walk("D:\\"):
				print("当前遍历目录:%s"%(root))
				print("当前目录中的目录:%s"%(dirs))
				print("当前目录中的文件:%s"%(files))


	# 修改==================
	None renames(oldname,newname)  
		* 重命名文件/目录
	
	None chmod(path,model)
		* 修改权限

	# 信息==================
	os.stat_result stat('path/filename')  
		* 获取文件/目录信息
		* os.stat_result(st_mode=16895, s
		t_ino=1407374883553285,
		st_dev=3424898185,
		st_nlink=1, 
		st_uid=0, 
		st_gid=0, 
		st_size=8192,			# 文件/目录大小
		st_atime=1497075122,	# 最后存取时间
		st_mtime=1497075122,	# 创建时间
		st_ctime=1459282700)	# 最后修改时间

	
	# 命令==================
	int system("bash command") 
		* 运行shell/cmd命令直接显示
		* 返回值如果是 0 则表示执行OK
	
	# 获取安全的随机数
		urandom(num)			
			* num 表示随机数的长度
			* 返回的 byte[]
				base64.b64encode(os.urandom(32))

	os._wrap_close popen("bash command"")
		* 执行命名,获取对象
		* 执行该返回对象的read(),可以获取到命名执行后的结果
			read()

	# path 操作相关==================

	str path.abspath(path)  
		* 返回path规范化的绝对路径
		* 返回指定路径的绝对路径

	tuple path.split(path)  
		* 返回元组
		* 第一个元素就是当前文件/目录,所在的目录
		* 第二个元素就是当前文件/目录,的名称

	str path.dirname(path)  
		* 返回path所在的目录
		* 其实就是os.path.split(path)的第一个元素

	str path.basename(path)  
		* 返回path最后的文件/文件夹名.如果path以'／'或'\'结尾,那么就会返回空值('')
		* 即os.path.split(path)的第二个元素
	
	path.exists(path)  
		* 如果path存在,返回 True:如果path不存在,返回 False

	path.isabs(path)  
		* 如果path是绝对路径,返回True
	
	path.realpath(path)
		* 返回指定文件的绝对路径
		* 当前Python脚本的绝对路径:os.path.realpath(__file__)

	path.isfile(path)  
		* 如果path是一个存在的文件,返回 True,否则返回 False

	path.isdir(path)  
		* 如果path是一个存在的目录,则返回 True 否则返回 False

	path.join(path1, path2, ...)  
		* 将多个路径组合后返回
		* 如果其中一个(参数)路径是绝对路径,那么该路径(参数)前的所有路径会被忽略
	
	path.split(path)
		* 把一个路径拆分为两部分,后一部分总是最后级别的目录或文件名
		* 返回 tuple

	path.getatime(path)  
		* 返回path所指向的文件或者目录的最后存取时间

	path.getmtime(path)  
		* 返回path所指向的文件或者目录的最后修改时间
	
	path.splitext(path)
		* 获取文件的后缀名称
		* 返回 type,[1]角标就是后缀
	
	# 线程相关
	int getppid()
		* 返回父进程(创建了当前进程的进程)的PID
	
	int getpid()
		* 返回当前进程的ID
	
	fork()
		* 创建新的进程,但是,只能在 Unix/Linux 上用

-----------------------
os._wrap_close			|
-----------------------
	str read()
		* 获取,执行了shell命名后的输出

-----------------------
demo					|
-----------------------
# 递归删除目录/文件
def deleteFile(path):
    assert os.path.exists(path),'删除目标:[%s]必须存在'%(path)
    if(os.path.isfile(path)):
        os.remove(path) # 不是目录,直接删除
    else:
        subFiles = os.listdir(path)
        if(len(subFiles) > 0):
            for file in subFiles:   # 存在一个或者多个子目录/文件
                file = os.path.join(path,file)# 目录拼接
                deleteFile(file)    # 递归处理     
        os.rmdir(path)              # 删除目录

# 递归遍历目录,找出包含指定名称的文件
def find_file(target_dir,file_name):
    assert os.path.isdir(target_dir),"目标目录[%s]必须存在"%(target_dir)
    result = list()
    def func(target):
        files = os.listdir(target)
        if(len(files) > 0):
            for file in files:
                file = os.path.join(target,file)
                if os.path.isfile(file) :
                    print("找到文件: %s"%(file))
                    if(file.find(file_name) != -1):
                        result.append(os.path.abspath(file))
                else:
                    func(file)
    func(target_dir)
    return result


# 递归的修改文件/文件夹,对其添加前缀,以及对关键字的替换
def rename_files(file,pre_fix='',repalce=None):
    assert os.path.exists(file) ,'目标文件[%s],必须存在'%(file)
    if os.path.isdir(file):
        files = os.listdir(file)
        if(len(files) > 0):
            for sub_file in files:
                rename_files(os.path.join(file,sub_file),pre_fix,repalce)
    file_name = os.path.basename(file)
    if repalce is not None:
        file_name = repalce(file_name)
    os.renames(file, os.path.dirname(file) + os.sep + pre_fix + file_name)

def replace(name):
    return name.replace('','')

rename_files("D:\\note", "javaweb")
