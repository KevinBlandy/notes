————————————————————————————————
1,AJAX的整合					|
————————————————————————————————
	> AJAX,异步提交,不多做解释了.
	* 步骤
		1,得到XMLHttpRequest对象
		2,打开与服务器的连接
		3,发生请求
		4,得到响应

————————————————————————————————
1,struts2与AJAX的整合			|
————————————————————————————————
	> struts2,中提供了json插件
	['步骤']
	1,导入json插件包
		* lib目录下:struts2-json-plugin-x.x.x.x.jar
	2,在xml配置文件中,自己的Action配置,package不在继承:struta-destul,而是继承:json-default
		* json插件包中有一个xml,配置了一个package,而且这个package是继承了stuts-default
		* json里面的xml,就是实现了一些关于json操作的拦截器,这些拦截器会把Action中栈顶的数据进行json
		<package extends="json-default">
	3,在响应异步Action配置文件中,reuslt要有"json"这个type属性!
		* <resutl type="json"/>
	

	* 经过这步骤后,会把ValueStack栈顶数据变成Json,
	  对于我们的程序,也就是会把Action对象.变为json
	
['拓展']
	在xml的配置中,type为"json"的result视图可以设置一个属性	
		<result type="json">
			<param name="root">Action属性</param>
		</result>
	* 这样配置的话,就不是默认的把整个Action作为json,而是指定的Action中的属性名
	
	'设置转换成的json对象中不包含特定的属性'
	1,@JSON(serialize=false)
		* 把这个注解,放置到getXxx方法上!那么这个属性就不会被json解析出来!
		* 写在Model的某个字段上啊!
	2,通过JSON插件的,Interceptor 完成!
		<result type="json">
			<param name="root">Action属性</param>
			<param name="includeProperties">ps[/d+]..妈的很恶心的写法</param>
		</result>
		* 这东西很恶心,需要使用的时候自己去查询吧!麻痹的,struts2搞不好还真没SpringMVC好使.太特么沉重复杂了!
		* 太美的承诺,只因为太年轻
	
	
	
