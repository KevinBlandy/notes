
//////////////////////////
	SptingMVC,对json的数据交互的支持		'重点'
	Json,是一种数据格式,文本数据交互格式!在接口调用,前台页面比较常用,
	Json,这东西简单.解析方便!
	比如:WebService
	
	对于Json处理的分析
	当客户端把Json串传递到服务器的时候,SptingMVC再进行参数绑定的时候,会使用 @RequestBody 把Json串解析成为JAVA对象!传递给形参!
	逻辑处理完毕之后,再把java对象,通过 @ResponseBody 解析为json数据.相应给客户端!

	@RequestBody	--> 把json转换为java对象(POJO,List)
	@ResponseBody	-->	把java对象转换为json

	* 当客户端请求的不是json,那就不需要使用 @RequestBody 注解来进行解析
	* 但是,如果需要给客户端响应json,那么就可以使用到 @ResponseBody 来进行转换

	总结:
		1,请求json,响应json
			* 请求参数是json,这东西就要求使用js来做数据解析,对于客户的的要求就会比较复杂
			* 但是有些丧心病狂的框架,就非要前端响应json.例如:... ...
		2,请求key-value,响应json
			* 使用比较多

/////////////////////////////////
环境准备
	1,SpringMVC,需要导入第三方的一个json解析器:
		jackson-core-asl-x.x.x.jar
		jackson-mapper-asl-x.x.x.jar
		* @RequestBody 和 @ResponseBody 就是使用上面俩包进行json转换
	
	2,在注解适配器加入:messageConverters
		<!-- 注解:处理器适配器 -->
		<bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter">
			<property name="messageConverters">
				<list>
					<!-- json转换器 -->
					<bean class="org.springframework.http.converter.json.MappingJacksonHttpMessageConverter"/>
				</list>
			</property>
		</bean>
		* 如果是使用<mvc:annotation-driven/>,那么.就不用定义,'已经默认加入'.
	
////////////////////////
	1,输入的是json串,响应的也是json
		* 客户端请求头:contentType:application/json;charset=utf-8
		* 服务器响应头:contentType:application/json;charset=utf-8
		
		在处理器方法的形参上标识注解:@RequestBody  就能把请求的json串解析为标识的对象
		public @ResponseBody User jsonDemo(@RequestBody User user)
		{
			return user;
		}

	2,输入的是key-value,响应的是json
		* 客户端请求头:contentType:application/x-www-form-urlencoded
		* 服务器响应头:contentType:application/json;charset=utf-8
		public @ResponseBody User jsonDemo(User user)
		{
			return user;
		}

	
	* @ResponseBody 也可以标注在Handler方法上!也是可以的,Handler的返回值也可以是 Collection<T>


/////////////////////////////////////////////////////
	MessageConverter

	# 只要是Controller中标记了 @ResponseBody 的方法,或者是返回值是 ResponseEntity<T> 的方法.都会使用MessageConverter来输出内容
	# 在配置了注解驱动后,默认装载了7个 MessageConverter ,注意顺序
		org.springframework.http.converter.ByteArrayHttpMessageConverter
			# 字节数组

		org.springframework.http.converter.StringHttpMessageConverter
			# 字符串转化,默认编码为:IOS-88-59-1
			# 就是这个坑,不知道害了多少人

		org.springframework.http.converter.ResourceHttpMessageConverter
			
		org.springframework.http.converter.xml.SourceHttpMessageConverter

		org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter

		org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter

		org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
			# json 转化
			# 只有它支持对象输出,也就是json.

	# 如果你的响应,没有指定数据类型(MediaType),spring会根据上面所说的数据,'从上往下查找.谁支持.就输出谁,不会再往下找了'
		

	# 自定义Converter
		<!--  注解驱动  -->
		<mvc:annotation-driven>
			<mvc:message-converters>
				<!-- 处理掉响应为:text文本时,默认使用:IOS-8859-1 的问题 -->
				<bean class="org.springframework.http.converter.StringHttpMessageConverter">
					<!-- 通过构造方法,指定编码 -->
					<constructor-arg index="0" value="UTF-8"/>
				</bean>
			</mvc:message-converters>
		</mvc:annotation-driven>
		
		* 自定义了,MessageConverter后,是否再注册默认的MessageConverter,由 mvc:message-converters 标签的 register-defaults="boolean" 属性指定

		* register-defaults="true"(默认值)	--> 还会注册默认的Converter(加上默认的,就有八个了.而且'自定义的要排到前面')

		* register-defaults="false"			--> 不会注册默认的Converter


	
//////////////////////////////////////////////////////
	JSONP--传说中的跨域, SpringMVC的解决方案
	1,继承:org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
		
	2,覆写:
		import java.io.IOException;
		import javax.servlet.http.HttpServletRequest;
		import org.apache.commons.io.IOUtils;
		import org.springframework.http.HttpOutputMessage;
		import org.springframework.http.converter.HttpMessageNotWritableException;
		import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
		import org.springframework.util.StringUtils;
		import org.springframework.web.context.request.RequestContextHolder;
		import org.springframework.web.context.request.ServletRequestAttributes;
		import com.fasterxml.jackson.core.JsonEncoding;
		import com.fasterxml.jackson.core.JsonProcessingException;


		// 做jsonp的支持的标识，在请求参数中加该参数
		private String callbackName;
		/**
		 * 覆写执行方法 
		 * */
		protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
			// 从threadLocal中获取当前的Request对象
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			// 获取页面传递的callbackName,也就是跨越请求中jsonp的回调函数名
			String callbackParam = request.getParameter(callbackName);
			if(StringUtils.isEmpty(callbackParam)){
				// 没有找到callback参数，直接返回json数据
				super.writeInternal(object, outputMessage);
			}else{
				//存在callback参数,也就是跨域请求.那么使用回调函数包裹JSON对象
				JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
				try {
					String result = callbackParam+"("+super.getObjectMapper().writeValueAsString(object)+");";
					IOUtils.write(result, outputMessage.getBody(),encoding.getJavaName());
				}
				catch (JsonProcessingException ex) {
					throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
				}
			}
		}
		public String getCallbackName() {
			return callbackName;
		}

		public void setCallbackName(String callbackName) {
			this.callbackName = callbackName;
		}
		
	3,注册到SpringIOC中
		<mvc:annotation-driven>
			<mvc:message-converters>
				<!-- 处理掉响应为:text文本时,默认使用:IOS-8859-1 的问题 -->
				<bean class="org.springframework.http.converter.StringHttpMessageConverter">
					<!-- 通过构造方法,指定编码 -->
					<constructor-arg index="0" value="UTF-8"/>
				</bean>
				<!-- 
						自定义json解析,可以处理jsonp.跨域的问题.
						callbackName:定义前端传递过来的'回调函数参数名'的request域中的key值.可以灵活配置
				 -->
				<bean class="MappingJackson2HttpMessageConverter子类路径">
					<property name="callbackName" value="callback"/>
				</bean>
			</mvc:message-converters>
		</mvc:annotation-driven>




/////////////////////////////////////////////////////
JSON

	1,Json是什么?
		它是js提供的一种数据交换格式,不同语言之间的数据交互.
	2,应用场景
		WebService,Ajax异步交互... ...

	
一,JSON对象语法

	* 数据在名称/值对中
	* 数据由","逗号分隔,属性用'双引号括起来',单引号不行
	* 大括号保存对象
	* 方括号保存数组
		var person = {"name":"kevin","age":"18","gender":"男"};
		alert(person.name+","+person.age+","+person.gender);
	* Json的值
		* 数字		整数/浮点数
		* 字符串	在双引号中
		* 逻辑值	true/false
		* 数组		在方括号中
		* null
		var p = {"name":"kevin","age":18,"marry":false,"hobby":["LOL","CSOL","CSOL2"]};
		var p = {"name":"kevin","age":18,"marry":false,"hobby":[{"name":"LOL"},{"name":"CSOL"},{"name":"CSOL2"}]};
	

二,Appache的小工具--json-lib
	* 这个工具可以把javaBean转换为json串!
	* 依赖的jar包有
		commons-lang.jar
		commons-beanUtils.jar
		commons-collections.jar
		ezmorph.jar
	
	* 核心对象
		JSONObject		--> Map
			put(Object,Object);		//其实就键值对,最后toString()就转换为Json串
			fromObject(Object);		//返回的就是一个 JSONObject对象,调用toString();就会把形参javaBean转换为一个json字符串
			toString();				//返回的就是一个json串

		JSONArray		--> List
			add(Object);			//添加一个对象,到最后toString();就会把容器里面的所有的对象都变成一个json字符串
			fromObject(Object);		//返回的还是JSONArray,传入一个List对象,直接把List集合里面的元素都转换成json!
			toString();				//不解释
			
===========================================================================================================
package com.kevin.json;
import java.util.ArrayList;
import java.util.List;
import com.kevin.domain.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * Appache-Json转换工具演示
 * */
public class Json
{
	/**
	 * 核心类,核心方法:
	 * JSONObject		--> Map
	 * 		> toString();
	 *		> fromObject(Object);
	 * JSONArray		--> List
	 * 		> toString();
	 *		> fromObject(Object);
	 * */
	public static void main(String[] args)
	{
		demo1();
		demo2();
		demo3();
		demo4();
	}
	/**
	 * 当作Map来使用
	 * */
	public static void demo1()
	{
		JSONObject map = new JSONObject();
		map.put("name","kevin");
		map.put("age",18);
		map.put("marry",false);
		System.out.println(map.toString());
		//{"name":"kevin","age":18,"marry":false}
	}
	/**
	 * 把javaBean转换为Json
	 * */
	public static void demo2()
	{
		User user = new User("F8575532","Kevin",22,false);
		JSONObject map = JSONObject.fromObject(user);
		System.out.println(map.toString());
		//{"age":22,"id":"F8575532","marry":false,"name":"Kevin"}
	}
	/**
	 * 当作List来使用
	 * */
	public static void demo3()
	{
		User a = new User("F8575532","Kevin",22,false);
		User b = new User("F8575852","Litch",24,true);
		JSONArray list = new JSONArray();
		list.add(a);
		list.add(b);
		System.out.println(list.toString());
		//[{"age":22,"id":"F8575532","marry":false,"name":"Kevin"},{"age":24,"id":"F8575852","marry":true,"name":"Litch"}]
	}
	/**
	 * 使用自定义List
	 * */
	public static void demo4()
	{
		User a = new User("F8575532","Kevin",22,false);
		User b = new User("F8575852","Litch",24,true);
		List<User> arr = new ArrayList<User>();
		arr.add(a);
		arr.add(b);
		JSONArray list = JSONArray.fromObject(arr);
		System.out.println(list);
		//[{"age":22,"id":"F8575532","marry":false,"name":"Kevin"},{"age":24,"id":"F8575852","marry":true,"name":"Litch"}]
	}
}


	
