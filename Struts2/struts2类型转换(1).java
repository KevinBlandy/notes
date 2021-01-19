————————————————————————————————
一,struts2中提供的类型转换		|
————————————————————————————————
	struts2内部提供了大量的类型转换器,用来完成数据类型转换问题
	Date	-- >	"yyy-MM-dd"
	Boolean	--->	"true"/"false"
	Integer	-->		"1236"
	... ...
	strut框架能自动的把右边的字符串类型的数据,转换成左边的数据类型
	* 但是,如果格式不正确就有可能会抛出异常!

	一般,我们再开发中使用这些默认的转换器就够用了,但是也有特殊情况会使用到自定义的类型转换器
	例如:struts2中对 Date 的转换,默认是 "yyyy-MM-dd"!有时候我们需要做些改变!

————————————————————————————————
二,truts2中类形转换器的体系		|
————————————————————————————————
	com.opensymphony.xwork2.conversion
	* 这是struts2类型转换器的根接口.MyGod,我这把源码都给你复制过来了(就这么点)
		package com.opensymphony.xwork2.conversion;
		import java.lang.reflect.Member;
		import java.util.Map;
		public interface TypeConverter
		{
			public Object convertValue(Map<String, Object> context, Object target, Member member, String propertyName, Object value, Class toType);
			
			public static final Object NO_CONVERSION_POSSIBLE = "ognl.NoConversionPossible";
			
			public static final String TYPE_CONVERTER_CONTEXT_KEY = "_typeConverter";
		}
	* 它底下有N多的实现类.
		ArrayConverter
		CollectionConverter
		DateConverter
		EnumConverter
		NumberConverter
		StringConverter
		StrutsTypeConverter
		....
		* 看懂了没?strut2中的内置类型的转换器都是来自这个接口!
		* 在你知道了这个秘密以后,我们也可以实现这个接口来完成自定义的类型转换器
————————————————————————————————
二,truts2自定义类型转换器		|
————————————————————————————————
'大致步骤'
	1,创建自定义转换器类,实现'TypeConverter'接口
	2,重写:public Object convertValue(Map<String, Object> context, Object target, Member member, String propertyName, Object value, Class toType);方法
	3,注册类型转换器

详解
一,创建自定义转换器类
	1,实现TypeConverter需要重写convertValue这个很长的破方法!有6个参数,吓死人
		* 如果实现这个接口,它参数JB多了！所以... ...我们可以继承一个已经实现了这奇怪接口的类!
	2,继承DefaultTypeConverter
		* com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter
		* 只需要重写:public Object convertValue(Object value, Class toType)
		* 没那么多参数,就俩

二,覆写方法['方法详解']
		..不论进出,都是同一个方法.通过判断参数toType,的class类型来确定.这东西是从页面传来,还是从Action传出!
		执行逻辑代码后.你return的值,就是最终的值!

三,注册自定义类型转换器['有三种方式']
	1,'局部'--针对于Action
		* 这种方式,就是把Action当作Model处理!
		* 在Action类所在包下创建:'Action类名-conversion.properties'
		* 其他配置跟下面一模一样
	2,'局部'--针对于Model
		* 在Model类所在包下创建:'Model类名-conversion.properties'
		* 在文件中的转换定义格式为:属性名称=类型转换器的全类名
		* 例:birthday=com.kevin.util.MyTypeConverter
	3,'全局'--All
		* 全局就是针对于整个WEB项目,在指定文件中指出需要被转换的java具体类形!以及类型转换器全类名
		* 在src下创建一个文件:'xwork-conversion.properties'
		* 配置文件中书写格式:要转换的类型全名=自定义的类型转换器全类名
		* 例:java.util.Date=com.kevin.util.MyTypeConverter

四,'注意'
	1,result视图的影响
		对于struts2中,类型转换器!如果表单数据提交的时候,把数据向Model封装,出现了问题!
		那么会自动的把action的'result',改为'input'!也就是说我们需要准备好相应的视图,来配合struts2的自动校验
		如果,转换出现了异常,我们又没有定义'input'的result视图!那么就会抛出异常!
		'原理'
		struts-default.xml	中的两个个拦截器
			<interceptor-ref name="conversionError"/>					['它发现了异常转换的问题,记录到action']
			* 当类型转换出现问题的时候,它会向action中存储错误信息
			<interceptor-ref name="workflow">							['它发现action里面有报告的问题,不让拦截器往下执行,跳到input']
				<param name="excludeMethods">input,back,cancel,browse</param>
			</interceptor-ref>
			* 当前面的拦截器,出现了问题,并且把错误信息存放了Action中!那么这个拦截器就会自动的跳转到name="input"的result视图
			* 它而且也是默认的拦截器桟中的最后一第二个
		* 在对应的result页面,可以通过struts2的标签取出错误信息(记得导入标签库)
			* <st:fielderror/>
			* 英文的,可以通过国际化的处理!变成中文!
			
	2,原始转换形式被覆盖
		struts2的Date,原始的转型格式是:'yyyy-MM-dd'.如果我们自定义为:'yyyy/MM/dd'!那么原始格式将不会再起作用!
	3,一个properties文件中可以声明多个字段,也就是说可以配置多个类型转换器!
	4,如果是自定义类型转换器出现异常,需要跳转到name='input'的result视图!那么异常信息,catch到之后,抛出去:throw new RuntimeException(e);!不然拦截器无法捕捉到异常,是不会跳转的!

	
————————————————————————————————
二,truts2的神器					|
————————————————————————————————
	在定义类类型转换器上,还可以继承一个来自Apache的神器...
	org.apache.struts2.util.StrutsTypeConverter
	这是一个抽象类,它实现了上面的'DefaultTypeConverter'你可以自定义类去继承它！
	它的强大之处在于,它有俩方法在做类型转换这活儿上!特别牛逼!['源码在最后面']
	 
	* 其实推荐继承这个类:StrutsTypeConverter
	这个类,牛逼指出在于!分离了两个方法
	1,一个是,表单页面数据到Action的类型转换处理
	2,一个是,Action数据到表单页面类型转换的处理
	* 当然,这俩牛逼闪闪的方法,是抽象的.就需要我们自己去谱码!
	俩方法:
	public abstract Object convertFromString(Map context, String[] values, Class toClass);
	> 代表从表单页面提交到Action的数据转换方法
		* context,暂时别管.OGNL的东西
		* values:从表单传递过来的数据,是数组形式存放
		* toClass:就是要转换的类型(Model中对应字段的实体类型)!
	> 其实只用到values,就好!我们return什么,那么Model对应的字段就接收什么!
	public abstract String convertToString(Map context, Object o);
	> 代表从Action里面数据传递到页面的转换方法
		* context:OGNL的东西!
		* o:就是从我们Action里面出来的数据,我们要变成什么样子给页面,就return成什么样子!
	
['该抽象类源码']
package org.apache.struts2.util;
import java.util.Map;
import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;
public abstract class StrutsTypeConverter extends DefaultTypeConverter 
{
	public Object convertValue(Map context, Object o, Class toClass)
	{
		if (toClass.equals(String.class)) 
		{
			return convertToString(context, o);
		} 
		else if (o instanceof String[])
		{
			return convertFromString(context, (String[]) o, toClass);
		} 
		else if (o instanceof String) 
		{
			return convertFromString(context, new String[]{(String) o}, toClass);
		}
		else 
		{
			return performFallbackConversion(context, o, toClass);
		}
	}
	protected Object performFallbackConversion(Map context, Object o, Class toClass) 
	{
		return super.convertValue(context, o, toClass);
	}
	/**
		从页面到Action的转换规则
	*/
	public abstract Object convertFromString(Map context, String[] values, Class toClass);
	/**
		从Action到页面的转换规则
	*/
	public abstract String convertToString(Map context, Object o);//去的时候转换
}
['demo']
package com.kevin.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.struts2.util.StrutsTypeConverter;
/**
 * 自定义类型转换器
 * 继承StrutsTypeConverter抽象类
 * @author KevinBlandy
 * @date   2015年12月29日 19:40:18
 * */
public class MyTypeConverter extends StrutsTypeConverter
{
	/**
	 * 格式化
	 * */
	private String[] simpleDateFormats = new String[]{"yyyy/MM/dd","yyyy-MM-dd","yyyy MM dd","yyyy.MM.dd"};//格式化样板
	private Date date;
	/**
	 * 接收页面数据,转换为Action需要的数据
	 * */
	public Object convertFromString(Map context, String[] values, Class toClass) 
	{
		String value = values[0];
		try 
		{
			date = new SimpleDateFormat(simpleDateFormats[0]).parse(value);//转换异常,下一个转换格式
		} 
		catch (ParseException e)
		{
			try
			{
				date = new SimpleDateFormat(simpleDateFormats[1]).parse(value);//转换异常,下一个转换格式
			}
			catch (ParseException e1) 
			{
				try 
				{
					date = new SimpleDateFormat(simpleDateFormats[2]).parse(value);//转换异常,下一个转换格式
				} 
				catch (ParseException e2) 
				{
					try 
					{
						date = new SimpleDateFormat(simpleDateFormats[3]).parse(value);//转换异常,下一个转换格式
					} 
					catch (ParseException e3) 
					{
						throw new RuntimeException(e);//没辙了,都不在范围:抛,让那个叫啥的拦截器捕获!然后转发到input视图
					}
				}
			}
		}
		return date;
	}
	/**
	 * 接收Action数据,转换为页面需要的数据
	 * */
	public String convertToString(Map context, Object obj) 
	{
		return null;
	}
}
