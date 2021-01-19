----------------------------
Beetl-自定义格式化函数		|
----------------------------
	# 需要实现 Format 接口
		org.beetl.core.Format
	
	# 覆写方法
		public Object format(Object data, String pattern);
	
	# 参数
		data 
			* 参数表示需要格式化的对象，
		pattern
			* 表示格式化模式，开发时候需要考虑pattern为null的情况
		
	# 也可以实现 ContextFormat 类抽象方法，从而得到Context，获取外的格式化信息。
		* org.beetl.core.ContextFormat
	
	# 配置
		FT.xxFormate=x.x.x.x
		* 在模版中通过使用
			${data,xxFormate="pattern"}

			data		表示需要被格式化的对象
			xxFormate	表示配置文件中的配置的名称
			pattern		格式化的模式,可以不传.那么为null

			
