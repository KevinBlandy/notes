------------------------
DecimalFormat			|
------------------------
	# 强大的数字格式化工具, 继承:NumberFormat
	# DecimalFormat 类主要靠 # 和 0 两种占位符号来指定数字长度。
	# 0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。

	# 常用
	  // 可以无参数构造，有参数构造 参数有语法，详见底部。参考"0.0"，"0.0¤"，"0.0%"。
		DecimalFormat df = new DecimalFormat();
	  // 设置国家货币符号 参数为ISO 4217标准，如果构造参数添加‘¤’符号，参考--> 5211314￥
		df.setCurrency(Currency.getInstance("CNY"));
	  // 设置最多保留几位.参考--> 5211314.00
		df.setMaximumFractionDigits(2);
	  // 设置分组大小.参考--> 5,211,314
		df.setGroupingSize(3);
	  // 设置乘以的倍数.参考--> 521131400
		df.setMultiplier(100);
	  // 设置正数前缀,参考--> @5211314
		df.setPositivePrefix("@");
	  // 设置正数后缀 但是替换掉 已有字符 参考--> 5211314@
		df.setPositiveSuffix("@");
	  // 设置负数前缀,只对负数有效   参考-->@-1
		df.setNegativePrefix("@");
	  // 设置负数后缀 但是替换掉 已有字符  只对负数有效  参考--> -1@
		df.setNegativeSuffix("@");
	  // 设置四舍五入的模式 详见 RoundingMode 类 写的 非常详细
		df.setRoundingMode(RoundingMode.DOWN);
	  // 格式化 成 想要的结果
		df.format(5211314);

------------------------
demo					|
------------------------
		double pi=3.1415927;　//圆周率  
　　　　//取一位整数  
　　　　System.out.println(new DecimalFormat("0").format(pi));　　　//3  

　　　　//取一位整数和两位小数  
　　　　System.out.println(new DecimalFormat("0.00").format(pi));　//3.14  

　　　　//取两位整数和三位小数，整数不足部分以0填补。  
　　　　System.out.println(new DecimalFormat("00.000").format(pi));// 03.142  

　　　　//取所有整数部分  
　　　　System.out.println(new DecimalFormat("#").format(pi));　　　//3  

　　　　//以百分比方式计数，并取两位小数  
　　　　System.out.println(new DecimalFormat("#.##%").format(pi));　//314.16%  
　　  
		long c = 299792458;　　//光速  

　　　　//显示为科学计数法，并取五位小数  
　　　　System.out.println(new DecimalFormat("#.#####E0").format(c));　//2.99792E8  

　　　　//显示为两位整数的科学计数法，并取四位小数  
　　　　System.out.println(new DecimalFormat("00.####E0").format(c));　//29.9792E7  

　　　　//每三位以逗号进行分隔。  
　　　　System.out.println(new DecimalFormat(",###").format(c));　　　//299,792,458  

　　　　//将格式嵌入文本  
　　　　System.out.println(new DecimalFormat("光速大小为每秒,###米。").format(c));  

	# DecimalFormat 类主要靠 # 和 0 两种占位符号来指定数字长度。
	# 0 表示如果位数不足则以 0 填充，# 表示只要有可能就把数字拉上这个位置。

	