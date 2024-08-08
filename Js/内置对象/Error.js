-------------------------
Error
-------------------------
	# Error extends Object/Function
		* 异常对象
	
	# 构造函数
		new Error()
		new Error(message)
		new Error(message, options)
		new Error(message, fileName)
		new Error(message, fileName, lineNumber)

			message
				* 异常的文本消息
			
			options
				* 配置对象

				cause
					* 指示错误的具体原因，其实就是嵌套的异常对
					* 当捕获并重新抛出带有更具体或有用的错误消息的错误时，可以使用此属性传递原始错误。
					* 例如
						try {
						  frameworkThatCanThrow();
						} catch (err) {
						  throw new Error("New error message", { cause: err });
						}

-------------------------
this
-------------------------

-------------------------
static
-------------------------



-------------------------
内置的错误对象
-------------------------
	
