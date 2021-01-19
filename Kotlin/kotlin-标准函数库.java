----------------------------
标准函数库					|
----------------------------
	buildString()
		* 快速的建立, StringBuilder, 它接收一个 lambda 表达式
		* 它使用 apply, 已经绑定了一个上下文: StringBuilder
			var result = buildString {
				for (i in 0..9){
					append(i) // 在表达式中可以调用 StringBuilder 的所有方法
				}
			}

			println(result)
		
		* 最后返回 StringBuilder.toString()
	

					