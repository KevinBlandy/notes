————————————————————————————————
一,国际化						|
————————————————————————————————
	i18n,记得么?
	> 项目里面,一些固定的文本信息(菜单,错误信息,提示,时间,货币... ...)
	> 这东西吧,大项目!跨国际才用到!
	> 根据客户端浏览器的配置信息,来决定我们要加载哪个'properties配置文件'!每个地区都有对应的配置文件,就4这么简单!

————————————————————————————————
二,struts2国际化				|
————————————————————————————————
	2,准备资源文,遵从一定的命名格式
		baseName_language_country.properties
		baseName_language.properties
		baseName.properties
	> baseName 是资源文件的基本名称,我们可以自己定义
	> language 代表的是语言,必须是JAVA可以识别的静态变量 
	> country  代表国家,也必须是JAVA可以识别的关键字

[关键问题]
	1,struts2,中国际化文件(properties)怎么定义?
		可以在俩位置进行定义
		全局:['需要配置xml']
			> 在struts.xml中配置一个常量
			* <constant name="struts.custom.i18n.resources" value="资源路径名"/>
			* 其实这个常量是定义在default.properties文件中的:#struts.custom.i18n.resources=testmessages,testmessages2
			* value的值.就是在src目录,你的资源文件名!注意,没有后缀!如果有包,那么你就要带上包名!
			* 也就是说,proerties配置文件,可以在src的任何目录下!
		局部:['不需要配置xml']
			> 这东西比较麻烦
			1,针对于Action类的
				* 位置:与Action类在同一个包下
				* 名称:Action名称.properties
				* 该配置文件,只对当前Action有效
			2,针对于包下,所有Action的
				* 位置:在当前包下
				* 名称:包名.properties/不确定是包名.properties还是pacakge.properties!!需要验证!
				* 该配置对整个包内的Action以及xml配置文件都起作用
			3,给某个JSP使用,JSP临时使用某个properties文件
				* 在jsp页面导入struts2的标签库:<%@ taglib prefix="s" uri="/struts-tags" %>
				* 使用标签<s:i18n name="包名.配置文件名"/>,来加载配置文件
				* JSP很刁,可以使用上面的任何配置文件!

	2,在struts2中,国际化,可以在哪些位置使用?
		1,Acion类中使用
			> Action想要获取国际化资源文件的数据,必须继承ActionSupport
			* this.getText("msg");//直接取出proerties文件中name的值

		2,配置文件中使用(validation.xml...)
			> 使用场景:校验文件,里面定义的错误信息国际化!
			* <message key="msg"/>//这个就代表从国际化文件中取出msg的值
			* 注意是key属性!就从国际化文件中取值了
		3,JSP页面上使用
			> 导入struts2标签库:<%@ taglib prefix="s" uri="/struts-tags" %>
			* <s:i18n name="资源文件带包路径">
				<s:text name="msg"/>//就能在JSP页面取出,指定资源文件中的指定值
			  </s:i18n>
			* <s:text name="msg"/>//直接这样,就是拿全局配置文件中的msg的值

————————————————————————————————
三,struts2国际化动态文本		|
————————————————————————————————
	在struts2中,国际化配置文件中.使用动态文本!!!
	MessageFormat,记得不?

	例:在资源文件中有如下定义
key=hello {0}
1,在Action中使用带动态文本的资源文件
	this.getText("key",new String[]{"Kevin"});
	* 返回的就是:hello Kevin
	* 资源文件中key的值有几个{},那么在getText方法参数中就写几个.数组!
2,在JSP页面中使用
	<s:i18n name="配置文件地址">
		<s:text name="key">
			<s:param>kevin</sparam>//该标签就是为了格式化资源文件中的占位符{0}!
		</s:text>
	</s:i18n>
	* 输出结果就是Hello Kevin
3,在校验文件中... ...憋玩了!那个需要OGNL表达式.挺复杂!
懒得讲...百度去吧,看得懂就看!看不懂就算了!
