------------------------
自定义EL函数库			|
------------------------
	# 一般不用去折腾,别人写好的就够了.
	# 分为几个步骤,按部就班的来做就好了
		
------------------------
1,写一个JAVA类			|
------------------------
	# 定义0-N个静态方法
		1,必须是一个静态方法
		2,必须要有返回值

------------------------
2,写TLD文件				|
------------------------
	# 头文件,去现有的jar包中偷
		<?xml version="1.0" encoding="UTF-8" ?>
		<taglib xmlns="http://java.sun.com/xml/ns/j2ee"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd"
		version="2.0">
			<!-- 说明 -->
			<description>MyEl</description>
			<!-- 整个名字 -->
			<display-name>itcast-function</display-name>
			<!-- 版本 -->
			<tlib-version>1.0</tlib-version>
			<!-- 短称....烦人 -->
			<short-name>it</short-name>
			<!-- 
				这个其实现在随便写,如果是打了jar包就要严格了
			 -->
			<uri>http://www.kevinblandy.com/el/function</uri>
			<!-- 有一个函数,写一个部署 -->
			<function>
				<!-- 名字,随意 -->
				<name>fun</name>
				<!-- 类全路径 -->
				<function-class>com.kevin.el.MyEl</function-class>
				<!-- 
					对方法进行描述:返回值 方法名称();
				 -->
				<function-signature>java.lang.String test()</function-signature>
				<!-- 案例直接就不用给 
				<example></example>-->
			</function>
		</taglib>

------------------------
2,在JSP页面引入该约束	|
------------------------
	# <%@ taglib prefix="fn" uri="http://www.kevinblandy.com/el/function" %>
	# ${fn:fun()}