————————————————————————————————
一,struts2配置文件加载顺序		|
————————————————————————————————
struts2配置文件加载顺序
	struts框架要执行,必须先执行StrutsPrepareAndExecuteFilter前端控制器
		> init();初始化方法对Dispatcher进行了初始化!
		> Dispatcher的init()方法,里面就描述了struts2配置文件加载的顺序!


default.properties
struts-default.xml
struts-plugin.xml
struts.xml
struts.properties
web.xml
		> 在开发中,后加载文件的配置,会把先加载文件中的配置覆盖!

————————————————————————————————
二,struts.xml文件分离			|
————————————————————————————————
	<struts>
		<include file="">
	</struts>
	1,这东西吧,就是为了阅读方便!可以在strut.xml中导入其他的配置文件
	2,其他的配置文件,也必须遵守strut.xml的的规范.有相同的约束,以及strut根元素!
	当struts.xml中include包含了其他的xml文件！而这些其他的xml文件中,有相同的包名,以及Action名称！
	执行的顺序就是--->  哪个xml先被struts.xml引用！谁就执行!换句话说就是,谁在上！谁先执行！
	