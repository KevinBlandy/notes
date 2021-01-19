————————————————————————————————
一,struts2异常处理				|
————————————————————————————————
	> stuts默认的拦截器栈中,第一个拦截器!
	 <interceptor name="exception" class="com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor"/>
	* 拦截器的调用是一个'递归调用'的过程
	* 这个拦截器哥们儿在最顶端,其实啥也没做!没有直接就放行!它是第一个执行,也是最后一个执行!
	* 但是他,却有 try	catch!也就是说,只要是它以后的拦截器,其实都是在它的ctach范围内!只要这个范围内,出现异常,它都能捉到
	


	> 在action的操作中,出现问题.直接抛出自定义异常!
	> 其他的拦截器,或是action只要向外抛出异常,Exception拦截器就会捕获它,然后对数据进行处理!












————————————————————————————————
二,曾经的旧笔记					|
————————————————————————————————
struts2支持声明式异常处理
在ActionSuppoer这个类中的execute方法,就是往外抛了一个Exception
意思就是,谁覆写我这个方法,拜托别写trycatch,只管往外抛出,有struts2框架来进行"处理"

那么像传统的我们的那些个catch语句里面的throw new RuntimeException(e);,就可以向外抛了！
没关系,框架会给我"处理".

----单独配置
<action..>
	<exception-mapping result="error" exception="java.sql.SQLException"/>
	<result name="error">/error.jsp</result>
</action>
	> 意思就是在这个Action中如果捕捉到了java.sql.Exception,那么resutl就等于error,当然你就是要设置一个name为error的result处理方式

----全局配置错误捕捉,自己提供相应的Result处理
<package name="error" extends="struts-default">
	<global-exception-mappings>
		<exception-mapping result="error" exception="java.sql.SQLException"/>
	</global-exception-mappings>
</package>
	> 在一个包中配置一个全局的错误信息,然后只要继承它的包出现了异常,那么就会被捕获.result的值,也就被全局定义的异常给转换
	> 这种方式仍需要自己在自己的Action中提供result结果页面,
	> 全局异常负责捕捉,result的nam属性要跟全局的result属性相同

----全局配置错误捕捉,由全局统一处理
<package name="error" extends="struts-default">
 	<global-results>
		<result name="error">/error.jsp</result>
	</global-results>	
	<global-exception-mappings>
		<exception-mapping result="error" exception="java.sql.SQLException"/>
	</global-exception-mappings>
</package>
	> 不难看出,跟上面相比就是多了个全局的result,也就是说,继承了这个包的包的Action出现了异常之后都不用自己定义result来处理,由全局result来进行处理了

------原理讲述
请求 ---> 核心过滤器 --> 调用特定的Action的exceute()方法.

struts-default.xml中的defaultStack拦截器桟,就配置有一个叫做exception的拦截器
