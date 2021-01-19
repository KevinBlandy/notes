----------------------------
subprocess					|
----------------------------
	* 开启子进程,用于执行系统命令
	* 用于子进程管理

----------------------------
subprocess-属性				|
----------------------------
	PIPE = -1
	STDOUT = -2
	DEVNULL = -3

----------------------------
subprocess-函数				|
----------------------------
	Popen def __init__(self, args, bufsize=-1, executable=None,
                 stdin=None, stdout=None, stderr=None,
                 preexec_fn=None, close_fds=True,
                 shell=False, cwd=None, env=None, universal_newlines=None,
                 startupinfo=None, creationflags=0,
                 restore_signals=True, start_new_session=False,
                 pass_fds=(), *, encoding=None, errors=None, text=None)
		
		* 开启(创建)一个子进程,执行一个shell命令,返回一个 Popen 对象
		* 参数可以是数组,第一个元素是命令,剩下的则是命令参数
			Popen(["mkdir","t1"])
		* 参数也可以直接就是一个命令
			Popen("mkdir t2", shell=True)
		* 建议使用 with 语句
			with subprocess.Popen(...) as popen:
				pass

		* 关键字参数
			shell
				* bool 值
				* shell作为要执行的程序,如果shell值为True,则建议将args参数作为一个字符串传递而不要作为一个序列传递

			stdout	
				* 枚举值,指定命令执行后输出内容的管道
				* subprocess.PIPE		:由子进程转到父进程
			
			cwd
				* 如果该值不为None,则切换到该目录执行命令

			env
				* 环境变量,如果该值为None那么子进程的环境变量将从父进程中继承
				* 如果env!=None,它的值必须是一个映射对象
			
			universal_newlines
				* 如果该参数值为True,则该文件对象的stdin,stdout和stderr将会作为文本流被打开
				* 否则他们将会被作为二进制流被打开
			

	str getoutput(commond)
		* 执行SHELL命令,返回执行后的结果

	tuple getstatusoutput(commond)
		* 执行shell命令,返回元组,第一个数据是状态int值,第二个数据是执行后的结果
	
	check_output()
		* 父进程等待子进程完成
		* 如果执行异常会抛出:subprocess.CalledProcessError
		* 返回子进程向标准输出的输出结果
		* 关键字参数
			stderr
			shell
		* demo
			subprocess.check_output('dir',stderr=subprocess.STDOUT,shell=True)
	
	run(*popenargs, input=None, capture_output=False, timeout=None, check=False, **kwargs)
		* 执行指定的命令,等待命令执行完成后返回一个包含执行结果的 CompletedProcess 类的实例
			popenargs 
				* 要执行的shell命令,默认应该是一个字符串序列，如['df', '-Th']或('df', '-Th')
				* 也可以是一个字符串,如'df -Th',但是此时需要把shell参数的值置为True

			check
				* 如果check参数的值是True,且执行命令的进程以非0状态码退出,则会抛出一个CalledProcessError的异常,且该异常对象会包含 参数,退出状态码,以及stdout和stderr(如果它们有被捕获的话)


		
----------------------------
subprocess.Popen			|
----------------------------
	# 实例属性
		stdout
		stdint
		stderr
			* 返回标准的输入,输出,异常流
		pid
			* 返回pid

	# 实例函数
		stdout.read()
			* 读取执行结果,返回的结果是字节数据类型
			* 标准输出
		
		wait()
			* 当前线程阻塞,直到子进程的内容执行完毕
			* 关键字参数
				timeout
					*  指定超时的时间(秒),如果超时会抛出异常:TimeoutExpired
	
		poll()
			* 检查子进程是否结束,如果结束了返回状态码,否则返回 None
		
		terminate()
			* 停止该进程
		
		kill()
			* 杀死该进程
		
		send_signal(signal)
			* 给进程发送信号
		
		communicate(input=None, timeout=None)	
			* 与进程进行交互,比如发送数据到stdin,从stdout和stderr读取数据,直到到达文件末尾
			* 该方法中的可选参数 input 应该是将被发送给子进程的数据,或者如没有数据发送给子进程,该参数应该是None
			* input参数的数据类型必须是字节串,如果 universal_newlines 参数值为True,则input参数的数据类型必须是字符串
			* 该方法返回一个元组(stdout_data, stderr_data),这些数据将会是字节穿或字符串(如果universal_newlines的值为True)
			* 如果在timeout指定的秒数后该进程还没有结束,将会抛出一个TimeoutExpired异常,捕获这个异常,然后重新尝试通信不会丢失任何输出的数据
			* 但是超时之后子进程并没有被杀死,为了合理的清除相应的内容,一个好的应用应该手动杀死这个子进程来结束通信
			* 要注意的是,这里读取的数据是缓冲在内存中的,所以,如果数据大小非常大或者是无限的,就不应该使用这个方法


	# demo
		import subprocess
		with subprocess.Popen('dir',shell=True,stdout=subprocess.PIPE,stderr=subprocess.PIPE) as popen:
			# stdout=subprocess.PIPE 会把子进程执行的结果数据,传递给父进程.最终赋值给执行后的对象.如果没有该参数,会直接打印,而返回对象不能读取到执行结果
			result = popen.stdout.read()	# 读取输出
			error  = popen.stderr.read()	# 读取异常输出,如果没有异常,该值为None
			print(str(result,'GBK'))

		
		with subprocess.Popen("python",shell=True,stdin=subprocess.PIPE,stdout=subprocess.PIPE,stderr=subprocess.PIPE) as popen:
			popen.stdin.write(b"print \"Hello\"")
			(out,err) = popen.communicate()
			print(out)#b'Hello\n'
			print(err)#b''