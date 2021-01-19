-----------------------
FileVisitor				|
-----------------------
	# 文件递归接口
	# 一般都是在 Files 静态方法使用
		Path walkFileTree(Path start, FileVisitor<? super Path> visitor);
	# 预定义实现
		SimpleFileVisitor
			* 全部是默认返回 CONTINUE
			* 仅仅需要覆写需要的方法即可

	# 抽象方法
		FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
			# 它表示正在访问某个文件
		
		FileVisitResult visitFileFailed(Path file, IOException exc) 
			# 它表示访问某个文件的时候发生了异常
		
		FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			# 在访问一个目录之前执行,包括当前递归的目录
		
		FileVisitResult postVisitDirectory(Path dir, IOException exc)
			# 在访问完毕一个目录之后执行

	# 通过实现这4个方法并根据情况返回相应的遍历动作，程序可以很容易地控制整个遍历过程，并在遍历中对整个目录树进行修改。

-----------------------
FileVisitResult			|
-----------------------
	# 用来声明整个遍历过程的下一步动作。
	# 枚举值
		CONTINUE
			* 表示继续进行正常的遍历过程
		SKIP_SIBLINGS
			* 表示跳过当前目录或文件的兄弟节点
		SKIP_SUBTREE
			* 表示不再遍历当前目录中的内容
		TERMINATE
			* 表示立即结束整个遍历过程。

