关于struts2的国际化
不多说,直接来

1,准备资源文,遵从一定的命名格式
	baseName_language_country.properties
	baseName_language.properties
	baseName.properties
> baseName 是资源文件的基本名称,我们可以自己定义
> language 代表的是语言,必须是JAVA可以识别的静态变量 
> country  代表国家,也必须是JAVA可以识别的关键字
例如:
	> baseName_zh_CN.properties
	> baseName_en_US.properties

2,为我们的项目添加俩资源文件
--例如
>  第一个存放中文 demo.zh.CN.properties
	* welcome="欢迎"
>  第二个存放英文 demo.en.US.properties
	* welecome="welcome"

3,对于文中的属性,我们编写好了后,应该使用JDK提供的native2ascii命令把文件转换为unicode编码格式
> 命令
native2ascii 源文件 目标文件.properties

4,对与文使用位置的限定
1,定义为全局资源文件
	> 在struts.xml文件中,设置常量
	* <constant name="struts.i18n.encoding" value="资源文件基本名"></constant>

5,访问资源文件
1,在JSP页面中使用标签来输出 
	* <s:text name="welcome"/>	name属性的值,就是资源文件中的key
2,在Action类中,可以继承ActionSupport.使用getText(String name)方法,得到国际化信息.name就是资源文件中的Key
3,在网页的表单标签中,通过key属性指定资源文件中的key
例如:
	<s:textfield name="realname" key="user"/>


6,输出带有占位符的国际化资源
1,资源文件中进行修改
	welcome={0}欢迎{1}	//中文的
	welcome={0}welcome{1}	//英文的
2,在JSP页面输出带占位符的资源
<s:text name="welcome">
	<s:sparam>
		<s:property value="1>
	</s:sparam>
</s:text>

3,在Action类中获取带有占位符的国际化资源
使用:
	getTest(String key,String[] agrs);
	getTest(String name,List ages);




---------------
ResourceBundle res = ResourceBundle.getBundle("资源文件名,",Local.US);
System.out.println(res.getString("key"));

*	资源文件名,就是加载的哪个资源文件,会根据后面的Local.US来进行判断,这个是代表美国,如果是中国,那么就会加载中国的properties文件
*	res.getString("key");,其中key就是需要被翻译成外语的关键字,例如:登录,在汉语中是登录,英语中就是Login