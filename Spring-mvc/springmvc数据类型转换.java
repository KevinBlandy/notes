

	SptingMVC中,接收页面提交的数据是通过形参来接收,而不是 struts2那样通过成员变量.
	
		1,客户端请求,携带key-value数据
		2,处理器适配器调用springmvc提供的'参数绑定组件',把key-value数据转换为controller方法的形参
	


	参数绑定组件
		1,框架早期的时候,使用PropertEditor,只能把字符串转为JAVA对象
		2,而后期就使用 Converter,可以进行任意类型的转换
		  springmvc提供了很多Converter(转换器),在特殊情况下,需要自定义Converter	
		3,把key-value的数据绑定到方法的形参

['参数绑定默认支持的类型']
		* 也就是Controller形参支持的类型
		基本数据类类型
		POJO
		HttpServletRequest
		HttpServletResponse
		HttpSession
		Model/ModelMap
			* ModelMap是Model接口的实现类,通过Model或者ModelMap向页面传递数据.这俩东西其实是一回事.模型数据填充到request域.
		
		* 通过JAVA反射,获取到方法的形参,如果发现了上述类型.那么就进行参数绑定
		


['Date之类的数据类型处理']
		* 实现日期类型的参数绑定.
		* 需要向处理器适配器中,注入我们自定义的参数绑定组件


		<!-- Springmvc注解驱动 -->
		<mvc:annotation-driven conversion-service="conversionService"/>
		<!-- 类型转换器 -->
		<bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
			<!-- 自定义类型转换 -->
			<property name="converters">
				<list>
					<bean class="com.kevin.conversion.DateConversion"/>
				</list>
			</property>
		</bean>


	package com.kevin.conversion;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.Date;
	import org.springframework.core.convert.converter.Converter;
	/**
	 * 日期格式转换器
	 * 	String:一般不变,表单数据
	 * 	Date  :经过转换后的结果数据类型(也就是Controller的处理方法的形参类型)
	 * */
	public class DateConversion implements Converter<String,Date>
	{
		//字符串数据格式
		private String[] types = new String[]{
				"yyyy-MM-dd",
				"yyyy/MM/dd",
				"yyyy.MM.dd",
				"yyyy年MM月dd日"
		};
		/**
		* 需要注意的是,该方法的返回值类型,必须要和Handler中的形参/POJO中的属性,!完全相同
		**/
		public Date convert(String date)
		{
			try 
			{
				return new SimpleDateFormat(types[0]).parse(date);
			}
			catch (ParseException e)
			{
				try 
				{
					return new SimpleDateFormat(types[1]).parse(date);
				}
				catch (ParseException e1) 
				{
					try 
					{
						return new SimpleDateFormat(types[2]).parse(date);
					}
					catch (ParseException e2) 
					{
						try
						{
							return new SimpleDateFormat(types[3]).parse(date);
						}
						catch (ParseException e3)
						{
							return null;
						}
					}
				}
			}
		}
	}

		-----------------------

		Converter<源类型,目标类型>
		public 目标类型 convert(源类型)
		{
			//逻辑代码
		}
	

	/***
		枚举转换
	**/
	public class EnumConverter implements Converter<String,Enum<?>>{

		@SuppressWarnings("unchecked")
		@Override
		public Enum<?> convert(String source) {
			return Enum.valueOf(Enum.class, source.toUpperCase());
		}
	}


============================赘述
自定义类型转换器
	* ConversionService 是Spring类型转换的核心接口.
	* 可以利用:ConversionServiceFactoryBean在Spring的IOC中创建一个ConversionService!
	* Spring可以自动识别,出IOC容器中的ConversionService,并在Bean属性配置及SpringMVC处理方法形参绑定等场合,使用它进行数据的转换
	* 可通过ConversionServiceFactoryBean 的converters属性注册自定义的类型转换器

	SpringMVC 支持的转换器
		* Spring定义了3种类型的转换器接口,实现任意一个转换接口都可以作为自定义转换器注册到ConversionServiceFactoryBean中
		1,Converter<S,T>	['常用']
			* 把S类型转换为T类型,一般而言.S都是客户端传递的参数.都是为String类型
		2,ConverterFactory
			* 把相同系列多个"同质",Converter封装在一起,如果希望把一种类型的对象转换为另一种类型及其子类的对象
			* 例如:把String 转换为Number,以及Number子类,就可以使用这个转换器工厂类
		3,GenericConverter
			* 会根据源类对象以及目标类对象所在的宿主类的上下文信息进行类型转换.
		

		<!-- Springmvc注解驱动 -->
		<mvc:annotation-driven conversion-service="conversionService"/>
		<!-- 类型转换器 -->
		<bean id="conversionService" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
			<!-- 自定义类型转换 -->
			<property name="converters">
				<list>
					<bean class="com.kevin.conversion.DateConversion"/>
				</list>
			</property>
		</bean>


