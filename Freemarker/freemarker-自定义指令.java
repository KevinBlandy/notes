------------------------
以使用 macro 指令来定义	|
------------------------
	# 基本定义
		<#macro greet>
			<font size="+2">Hello Joe!</font>
		</#macro>

		* 指令名称 greet
		* 值定义指令是用:@
		* 使用:<@greet></@greet> 或者 <@greet/>
	
	# 变量会在模板开始时被创建
		* 也就是说,可以在调用指令后创建指令(其实指令在调用之前会先初始化)

	# 指令之间的东西是模板片段,可以使用任意的ftl语法
		* 可以嵌套其他的自定义指令

	# 参数的定义与使用
		<#macro foo var1 var2>
			<font size="+2">${var1} - ${var2}</font>
		</#macro>

		<@foo users[0].name "KevinBlandy"/>
			*  如果不指定名称,则参数按照顺序传递

		<@foo var1=users[0].name var2="KevinBlandy"/>
			*  通过指定标签属性来设置指定的参数
		
		* 如果定义了参数,则必须都要传递,少传递参数会发生异常
		* 也不能多传递参数,或者传递指令未定义的参数,不然也会发生异常
		
		* 指令可以带默认值,这样的话,使用的时候,可以省略这个参数
		* 没有默认值的参数必须在有默认值参数 (paramName=defaultValue) 之前
			<#macro foo var1 var2="default">
				<font size="+2">${var1} - ${var2}</font>
			</#macro>
			@foo var1=users[0].name/>
		
		* 自定义指令里面使用的参数,都是局部变量
	
	# 可变参数
		* 最后一个参数,可能会有三个点(...), 这就意味着宏接受可变数量的参数
		* 不匹配其它参数的参数可以作为最后一个参数 (也被称作笼统参数)
		* 当宏被命名参数调用,paramN 将会是包含宏的所有未声明的键/值对的哈希表
		* 当宏被位置参数调用,paramN 将是额外参数的序列 (在宏内部,要查找参数,可以使用 myCatchAllParam?is_sequence)

		* 可变参数,可以一个map,也可以是一个集合
			<#macro foo v1 v2 v3...>
				<#if v3?is_sequence>
					//是集合
					<#list v3 as i>
						${i}<br/>
					</#list>
					
					//是map
					<#else>
					
					<#list v3?keys as key>
						${v3[key]}<br/>
					</#list>
				</#if>
				
			</#macro>

			<@foo v1="1" v2="2" aa="3" bb="4"></@foo>
				* 这种调用方式可变参数是map

			<@foo "1" "2" "3" "4"></@foo>
				* 这种调用方式可变参数是集合

	
	# 指令体的插入
		<#macro foo>
			Hello<#nested>
		</#macro>

		<@foo>
			<span style="color:red">Java</span>
		</@foo>

		Hello<span style="color:red">Java</span>

		* <#nested> 替换为使用指令时的标签体

		* <#nested> 指令也可以多次被调用
			<#macro foo>
				Hello<#nested>
				<#nested>
				<#nested>
			</#macro>
	
	# 在嵌套的内容中,宏的局部变量是不可见的
		<#macro repeat count>
			<#local y = "test">			//局部变量
			<#list 1..count as x>		
				${y} ${count}/${x}: <#nested>
			</#list>
		</#macro>

		<@repeat count=3>
			//不能访问到指令内部定义的变量
			${y!"?"} ${x!"?"} ${count!"?"}<br/>
		</@repeat>
		
		test 3/1: ? ? ?
		test 3/2: ? ? ?
		test 3/3: ? ? ?
	
	# 循环变量
		* <#nested />可以设置一个循环变量
		* 使用标签的时候,可以渲染该变量
			<#macro foo>
				<#nested 1/>
				<#nested 2/>
				<#nested 3/>
			</#macro>
			<@foo ;x>
				name ${x}<br/>
			</@foo>
			name 1
			name 2
			name 3

		* 环变量的名称是在自定义指令的开始标记(<@...>) 的参数后面通过分号确定的
		* 一个宏可以使用多个循环变量(变量的顺序是很重要的)
			<#macro foo count=10>
				<#list 1..count as item>
					<#nested item,item==1,item==count/>
				</#list>
			</#macro>
			
			<@foo ;index,first, last,a>
				${index} ${first?string("第一个","不是第一个")} ${last?string("最后一个","不是最后一个")}<br/>
			</@foo>

	# 中止指令 #return
		<#macro test>
		  Test text
		  <#return>
		  Will not be printed.	//会被忽略
		</#macro>
------------------------
通过编码来完成自定义	|
------------------------
	# 实现接口:TemplateDirectiveModel
		import java.io.IOException;
		import java.util.Map;

		import freemarker.core.Environment;
		import freemarker.template.TemplateDirectiveBody;
		import freemarker.template.TemplateDirectiveModel;
		import freemarker.template.TemplateException;
		import freemarker.template.TemplateModel;

		public class FooDirective  implements TemplateDirectiveModel{

			/**
			 * @param env				运行环境
			 * @param params			参数
			 * @param loopVars			循环参数
			 * @param body				指令体
			 */
			@SuppressWarnings("rawtypes")
			@Override
			public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)throws TemplateException, IOException {
				//渲染
				body.render(env.getOut());
			}
		}
	
	# 添加指令到模板变量
		* configuration.put("foo", new FooDirective());
		* 可以设置为共享变量,以单例形式存在
	
	# 在模板引擎中使用
		<@foo></@foo>



------------------------
demo					|
------------------------
	# 一个 upper 指令,让该指令之间的输出都变成大写
		import java.io.IOException;
		import java.io.Writer;
		import java.util.Map;

		import freemarker.core.Environment;
		import freemarker.template.TemplateDirectiveBody;
		import freemarker.template.TemplateDirectiveModel;
		import freemarker.template.TemplateException;
		import freemarker.template.TemplateModel;
		import freemarker.template.TemplateModelException;

		public class UpperDirective implements TemplateDirectiveModel {

			@SuppressWarnings("rawtypes")
			public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
					throws TemplateException, IOException {
				if (!params.isEmpty()) {
					throw new TemplateModelException("该指令不允许有标签");
				}
				if (loopVars.length != 0) {
					throw new TemplateModelException("该指令不允许有循环变量");
				}

				if (body != null) {
					body.render(new UpperCaseFilterWriter(env.getOut()));
				} else {
					throw new RuntimeException("未定义标签体");
				}
			}

			private static class UpperCaseFilterWriter extends Writer {

				private final Writer out;

				UpperCaseFilterWriter(Writer out) {
					this.out = out;
				}

				public void write(char[] cbuf, int off, int len) throws IOException {
					char[] transformedCbuf = new char[len];
					for (int i = 0; i < len; i++) {
						transformedCbuf[i] = Character.toUpperCase(cbuf[i + off]);
					}
					out.write(transformedCbuf);
				}

				public void flush() throws IOException {
					out.flush();
				}

				public void close() throws IOException {
					out.close();
				}
			}
		}