----------------------------
异常处理					|
----------------------------
	# 抛出异常,跟Java一样
		throw Exception("exception....")
	
	# 分支结构基本一样
		try {

		}catch (e : Exception){

		}finally {
			
		}
	
	# Kotlin 并不区分受检异常和未受检异常
		* 不用指定函数抛出的异常 , 而且可以处理也可以不处理异常
	
	# try catch 也是可以有返回值的
		* 代码块的最后一个表达式作为返回值
		* 这啥设计思想啊?

		var result = try {
			5
		}catch (e: Exception){
			4
		}

		fun mustFour(value:Int) = try {
				if (value == 4){
					4
				}else{
					throw Exception()
				}
			}catch (e:Exception){ // 异常了,则返回 1
				1
			}finally {
				-1				// 如果catch代码块还是异常,则返回 -1
		}

	
