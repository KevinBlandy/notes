我们知道,在JAVA中存在很多的特殊的数据类型
例如:Date,
但是客户端传递过来的,全部都是字符串数据,那么这时问题就出现了！
	
1,struts2收到客户端参数后,默认的会对Action的属性类型进行转换.但是转换的方式有时候不是我们需要的。例如:
	> Date类型
	* 客户端提交的数据必须是:1993-12-09这格式的数据,那么框架才会自动转换,如果属性名称匹配上了,但是参数格式没匹配,那么不会执行转换和注入
	* 默认的类型,在你自定义了格式规则后,尽量别用,会乱！

2,struts2有两种"类型转换器"
*************************************************局部类型转换器*************************************************
> 只对某一个Action起作用
1,自定义java类
  > 继承:DefaultTypeConverter
	* 该类全路径:com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
2,覆写convertValue方法(详细Demo看最后)
  > 覆写:public Object convertValue(Map<String, Object> context, Object value,Class toType)
	* 注意方法签签名,别覆写错了.该类有几个重载形式出现的同名方法j
  > context,先不管.ognl表达式d东西
  > Object value;
	> 这个值,就是我们从客户端接收到的值
	* 因为客户端传递的值不确定,所以框架直接就是Object然后,我们在使用的时候,需要强转成正确的类型,例如:数组,String
  > Class toType
	> 这个值,就是我们要转换的类型
	* 这个值,不用我们管!是框架读取了Action的属性之后得到的,这个值就是属性的Class
	* 一般用于我们做判断
---------代码
public Object convertValue(Map<String, Object> context, Object value,Class toType) 
{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");	//因为我们要对这个Action的Date字段进行转换所以定义这个自定义格式的日期格式化对象。
	//如果我们需要在参数传递,参数输出的格式不一样,那么可以把这个格式化对象建立到if判断语句中,不同定义格式就好
	try
	{
		if(toType == Date.class)		//如果框架读取到Action属性的Class 是Date,那么就把参数字符串,按照我们指定的格式转换成Date,返回赋给Action的date.
		{
			//字符串转Date
			String[] params = (String[]) value;//request.getParameterValues();
			return sdf.parse(params[0]);
		}
		else if(toType == String.class)		//当框架要把我们的Date输出到页面,也就是输出的时候是String类型,那么就把我们的Date给格式化成指定类型后,输出！
		{
			//Date转字符串
			Date date = (Date) value;
			return sdf.format(date);
		}
	}
	catch(ParseException e)
	{
		throw new RuntimeException(e);
	}
	return null;
}

3,注册
  > 第一步:因为是局部的,所以在需要被转换的Action同包下创建一个Poperties文件,命名方式是固定的,不能修改必须严格遵守
  * 命名规则:Action的简单类名-conversion.properties	例如:Demo-conversion.properties
  > 第二步:按照规则,配置该文件
  * 字段名=转换器路径
	> 字段名,也就是这个Action里面,你想要转换的字段名
	> 转换器路径,就是你写的那个转换器,注意,是带包全路径
	> 例如: date=com.kevin.filter.TypeDemo
  > 这个转换器,可以实现双向转换,也就是客户端到服务器,服务器到客户端！
  > 注意,不要导错包,例如:sql下的Date和util下的date.在判断的时候会出问题,别问我为什么知道。。。特么血的教训


*************************************************全局类型转换器*************************************************
	> 对整个项目起作用
1,同理,也是定义一个类,操作同上,我就不写了！唯一的不同就是配置文件的名堂不同
--不同之处
1,在WEB-INF/classes目录下!也就是src源代码目录下.创建指定名称的文件
	> xwork-conversion.properties
	* 这个格式打死也不能变,注意!
2,按照规则,配置文件
	> 数据类型=转换器带包全路径
	* 因为是全局转换器,那么不可能是定义某个Action的属性,这里的key也是一个带包全路径的JAVA类类型
	* 这里定义的是一个类型.代表着项目所有Action的这个类型都会经过后面的转换器处理
	* 例如:java.util.Date=com.kevin.filter.TypeDemo




