----------------------------
Beetl-实现Function			|
----------------------------
	# 实现 Function
		* org.beetl.core.Function

	# 在Beetl中,自定义 fun,是作为单例存在的

	# 覆写方法
		* public Object call(Object[] paras, Context ctx)
	
	# call方法要求返回一个Object，如果无返回，返回null即可
		* 为了便于类型判断，call方法最好返回一个具体的类
		* 如date函数返回的就是java.util.Date

	# call方法里的任何异常应该抛出成Runtime异常

	# 具备两个参数
		Object[] paras
			* 由模版传入的参数

		Context ctx
			* 模版上下文
	
	# Context 属性
		
		byteWriter		输出流
		template		模板本身
		gt				GroupTemplate
		globalVar		该模板对应的全局变量
		byteOutputMode	模板的输出模式，是字节还是字符
		safeOutput		模板当前是否处于安全输出模式
		* 其他属性建议不熟悉的开发人员不要乱动
	
	# 配置
		FN.methodName = xxx.xx.xxx.x
		* 在模版中通过 methodName(); 来完成调用 
	
----------------------------
Beetl-使用普通JAVA类		|
----------------------------
	# 尽管实现Function对于模板引擎来说，是效率最高的方式
	# 但考虑到很多系统只有util类，这些类里的方法仍然可以注册为模板函数。
	# 其规则很简单，就是'该类的所有public方法。如果需还要Context 变量，则需要在方法最后一个参数加上Context即可'
		public class util{
			public String print(Object a, Context ctx){
					//balabala...
			}
		}

	# 注意
		* 从beetl效率角度来讲，采用普通类效率不如实现Function调用
		* 采用的普通java类尽量少同名方法。这样效率更低。beetl调用到第一个适合的同名方法。而不像java那样找到最匹配的
		* 方法名支持可变数组作为参数
		* 方法名最后一个参数如果是Context，则beetl会传入这个参数。
	
	# 配置
		FNP.nameSpace = xxx.xxx.xxx.xx
		* 在模板中通过 nameSpace.方法名(参数); 来完成调用

----------------------------
Beetl-使用模板文件			|
----------------------------
	# 可以不用写java代码，模板文件也能作为一个方法。
	# 默认情况下，需要将模板文件放到Root的functions目录下，且扩展名为.html(可以配置文件属性来修改此俩默认值) 方法参数分别是para0,para1…..
	# 如下root/functions/page.fn

		<%
			//para0,para1 由函数调用传入
			var current = para0,total = para1,style=para2!'simple'
		%>
		当前页面 ${current},总共${total}
		则在模板中
		<%
			page(current,total);
		%>

		允许使用return 表达式返回一个变量给调用者，如模板文件functions\now.html

		<%
			return date();
		%>
		在任何模板里都可以调用：
		hello time is ${now(),'yyyy-MM-dd'}
		也可以在functions建立子目录，这样function则具有namespace，其值就是文件夹名
		