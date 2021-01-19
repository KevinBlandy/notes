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

========================
jsckson工具类,spring默认依赖的json类
private static final ObjectMapper MAPPER = new ObjectMapper();

MAPPER.writeValueAsString(Object obj)				//把对象序列化为json
MAPPER.readValue(String json, Class<T> clazz);	//把json反序列化为对象



========================
fastjson,阿里巴巴的神器
com.alibaba.fastjson.JSON;		//静态类


 JSON.toJSONString(Object obj);


//反序列化json对象,带泛型
List<Object> result = JSON.parseObject(json,new TypeReference<List<Object>>(){});

	
