
----------------------------
java13
----------------------------
	# 替换了java.net.Socket以及java.net.ServerSocket API的底层实现
		* 它使用 NioSocketImpl 来替换JDK1.0的PlainSocketImpl
		* 如果要继续使用旧版的Socket实现, 可以使用 -Djdk.net.usePlainSocketImpl 参数来切换到旧版本
	

	# 在 switch 中使用yield替换了break来避免歧义, 因为break可以用来进行跳转执行类似 goto 的操作
		String dayOfWeek = switch(1){
            case 1 -> {
                String day = "Monday";
                yield day;
            }
            case 2 -> {
                String day = "Tuesday";
                yield day;
            }
            default -> "Unknown";
        };
        System.out.println(dayOfWeek);
	
	# 文本块
		* 跟py的 ''' ''' 和 js的 `` 一样

			String desc = """
				Hello World;
			""";
		
		* 第一个""" 和第二个 """ 不能在同一行
	
