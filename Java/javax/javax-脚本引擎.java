--------------------
JAVA-脚本引擎		|
--------------------
	# 其实就是在JAVA代码中执行JavaScript的代码
	# JAVA只是提供了这个接口,但是没提供实现,由其他的脚本厂商自己编写实现.
	# 案例
		1,客户端传递了一个字符串
			"3 * 4 / 2 + 6 -5"
		2,该字符串应该用于计算最后的结果
		3,如果是使用JAVA来完成,就比较的麻烦
		4,如果是JavaScript那就简单 eval();函数就搞定

	# 脚本引擎是JDK6.0以后添加的新功能
		* 其实就是,JAVA应用程序可以通过一套固定的接口与'各种脚本引擎'交互
		* 从而达到在Java平台上调用各种脚本语言的目的
		* Java脚本API是连通Java平台好脚本语言的桥梁
		* 可以把一些复杂异变的业务逻辑交给脚本语言处理,这也大大提高了开发效率
		* 其实就是可以在java程序中执行其他的脚本语言
		* 6.0后javascript-->Rhino被添加到了JDK
	
	# Java脚本API为开发者提供了N多功能
		* 获取脚本程序输入,通过脚本引擎运行脚本并返回运行结果.
		* 核心的接口 : ScriptEngineFactory
			1,注意:是接口,JAVA可以使用各种不同的实现,从而调用js,groovy,python等脚本
			2,JavasScript  -->  RhinoEngineFactory
			3,Rhino 是一种使用java语言编写的javascript代码 (jdk.nashorn.api.scripting.NashornScriptEngine)
				* Rhino <<JavaScript权威指南>>,这本书封面就是Rhino

	# 获取脚本引擎Manager对象
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

	# 查看支持的脚本引擎
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		for (ScriptEngineFactory scriptEngineFactory : scriptEngineManager.getEngineFactories()) {
			System.out.println(scriptEngineFactory.getEngineName() + ":" + scriptEngineFactory.getEngineVersion()); // 执行引擎名称和版本号
			ScriptEngine scriptEngine = scriptEngineFactory.getScriptEngine(); // 获取到执行引擎
		}
			
	# 获取指定名称的脚本引擎
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");
	
	# 还可以根据mineType,扩展名等api去获取一个执行引擎
		scriptEngineManager.getEngineByMimeType("text/javascript");
		scriptEngineManager.getEngineByExtension("js");

	
	# 简单的使用
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();

		ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("javascript");

		scriptEngine.put("name", "KevinBlandy");

		Object result = scriptEngine.eval("\"Hello \" + name");

		System.out.println(result);	// Hello KevinBlandy
	
	

	# JDK11 开始, 废弃了这个引擎
		@Deprecated(since="11", forRemoval=true)
		public final class NashornScriptEngine extends AbstractScriptEngine implements Compilable, Invocable