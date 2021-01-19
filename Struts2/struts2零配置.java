————————————————————————————————
一,关于struts2的零配置			|
————————————————————————————————
	  首先说明一下,零配置跟注解没关系!注解也算是一种配置!

从struts2.1开始，struts2 引入了Convention插件来支持零配置
使用约定无需struts.xml或者Annotation配置
需要 struts2-convention-plugin-2.3.7.jar 、asm-*.jar(三个)
插件会自动搜索action、actions、struts、struts2包下所有Java类
所有实现了com.opensymphony.xwork2.Action的Java类
所有类名以Action结尾的Java类
下面类名都符合Convention插件
cn.kevin.struts2.HelloAction
cn.kevin.actions.books.BookSearchAction
cn.kevin.struts.user.UserAction
cn.kevin.estore.action.test.LoginAction


struts2-convention-plugin-2.3.7.jar 中struts-plugin.xml重要常量
<constant name="struts.convention.package.locators" value="action,actions,struts,struts2"/>  默认扫描包
<constant name="struts.convention.exclude.packages" value="org.apache.struts.*,org.apache.struts2.*,org.springframework.web.struts.*,org.springframework.web.struts2.*,org.hibernate.*"/> 不扫描
<constant name="struts.convention.action.suffix" value="Action"/> 默认扫描以Action结尾的类
<constant name="struts.convention.result.path" value="/WEB-INF/content/"/> 结果result页面存放位置
<constant name="struts.convention.classes.reload" value="false" /> Action类文件重新自动加载

如果Action类名包含Action后缀，将Action后缀去掉
将Action类名的驼峰写法，转成中划线写法
例如：
cn.kevin.struts2.HelloAction 映射到 /hello.action
cn.kevin.actions.books.BookSearchAction  映射到 /books/book-search.action
cn.kevin.struts.user.UserAction 映射到 /user/user.action
cn.kevin.estore.action.test.LoginAction 映射到 /test/login.action

默认情况下，Convention总会到Web应用的WEB-INF/content路径下定位结果资源
<constant name="struts.convention.result.path" value="/WEB-INF/content/"/>
约定： actionName + resultCode + suffix 
例如：
访问cn.kevin.struts.user.UserAction返回success
Convention优先使用 WEB-INF/content/user/user-success.jsp
如果user-success.jsp不存在，会使用user-success.html
如果user-success.html不存在，会使用user.jsp



