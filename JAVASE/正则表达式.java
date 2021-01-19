-------------------
正则表达式			|
-------------------
	# 符合一定规则的表达式
	# 用于专门操作字符串
	# 用特定的符号来表示一些代码操作,简化了书写
	Pattern 
	Matcher
-------------------
正则-匹配			|
-------------------
	boolean matches("正则表达式的字符串");
		# '任何字符串都可以调用该方法',如果匹配.返回 true,反之 false
		
-------------------
正则-切割			|
-------------------	
	String[] split(String regex);
		# 传递一个'正则表达式',
		# ['DEMO']
			//被匹配的字符串
			String str = "zhansan  lisi  wangwu";
			//正则表达式(代表一个或者多个空格)
			String reg = " +";
			//结果就是:zhangsanlisiwangwu   是根据正则(一个或者多个空格),来进行切割的
			String[] result = str.split(reg);
			//返回结果中就不包含-正则描述的字符串
	# '.',该符号在正则表达式中表示'任意字符'.所有在切割String的时候需要转义:'\\.'

-------------------
正则-替换			|
-------------------	
	 String replaceAll(String regex, String replacement);
	 # ['DEMO']
		//用于执行正则的字符串
		String str = "aaa000000aaa";
		//用于替换的新字符串
		String newStr = "#";
		//正则表达式(匹配到连续超过5个的数字类型的字符串)
		String reg = "\\d{5,}";
		str = str.replaceAll(reg, newStr);
		//结果就是:aaa#aaa			根据正则(连续超过五个数字类型的字符串),替换为指定的字符串
		System.out.print(str);

-------------------
正则-获取			|
-------------------	
	# 把字符串中符合规则的数据取出来
		1,先把正则表达式封装成对象
		2,让正则对象和要操作的字符串相关联
		3,关联后,获取一个叫做:'正则匹配引擎'
		4,通过引擎对符合规则的子串进行操作(比如,取出)
	


	API
		Pattern
			# 没有公共的构造函数,只有通过静态方法返回该类对象.
			Matcher matcher(CharSequence input)  
				* CharSequence 该类是字符序列接口.String 是它的子类.所以.可以直接放字符串、
			boolean matches(String regex, CharSequence input);
				* 静态方法,第一参数正则.第二个参数字符串.如果匹配返回true.反之返回false
			
		Matcher
			# 传说中的引擎,也就是匹配器
			boolean matches();
				* 字符串是否满足指定的正则描述.其实 String 内部的那个 matches();底层就是它
			boolean find();
				* 进行一次匹配操作,如果有匹配成功的数据.那么就返回 true,否则返回 false
			String group();
				* 用于获取匹配操作后的结果,一次group();仅仅对上一次的find();有效
				* 说白了,就是跟上面的相配合使用.一次find();返回true了.就可以执行一次grou();得到它匹配到的字符串
			int end();
				* 返回成功匹配到的子串的'结束偏移量'.(索引位置)
				* '必须先执行了find();'
				* 包含开头下标
			int start();
				* 返回成功匹配到的字串的'开始偏移量'.(索引位置)
				* '必须先执行了find();'
				* 不包含结束的下标
				

	/*
		 * 用于被匹配的字符串 
		 */
		String string = "aa aaa bbb ccc w aa a";
		/*
		 * 使用构造函数,创建出:正则对象
		 * 该正则的描述:3个连续的英文字符串
		 */
		Pattern pattern = Pattern.compile("[a-z]{3}");
		/*
		 * 让正则对象和要被操作的字符串相关联,获取--执行匹配操作的引擎(匹配器)
		 */
		Matcher matcher = pattern.matcher(string);
		/*
		 * find();进行匹配迭代,如果成功匹配到一条数据,返回true
		 */
		while(matcher.find()){
			/*
			 * 获取一次匹配的结果.也就是根据规则匹配出来的字符串
			 */
			String result = matcher.group();
			/*
			 * 该串在父串中的开始角标
			 */
			int sta = matcher.start();
			/*
			 * 该串在父串中的结束角标
			 */
			int end = matcher.end();
			System.out.println("符合的字符串:"+result);
			System.out.println("开始位置:"+sta+" 结束位置:"+end);
			/*
			 	结果--------------
			 	符合的字符串:aaa
				开始位置:3 结束位置:6
				符合的字符串:bbb
				开始位置:7 结束位置:10
				符合的字符串:ccc
				开始位置:11 结束位置:14
			 */
		}
	


		String string = "aa aaa bbb ccc w aa a";
		Pattern pattern = Pattern.compile("[a-z]{3}");
		Matcher matcher = pattern.matcher(string);
		while(matcher.find()){
			String result = matcher.group();
			int sta = matcher.start();
			int end = matcher.end();
			System.out.println("符合的字符串:"+result);
			System.out.println("开始位置:"+sta+" 结束位置:"+end);
		}