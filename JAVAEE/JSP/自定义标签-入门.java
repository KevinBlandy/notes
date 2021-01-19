------------------------------------
自定义JSTL标签						|
------------------------------------
	* 不多解释,直接正文步骤.
	一,大致步骤
		* 标签也是一个对象,那么就需要现有类,每一个标签其实都对应一个类
		* tld文件,它是一个xml.
		* 页面中使用<%@taglib>来指定tld文件的位置

------------------------------------
标签处理类							|
------------------------------------
	* 其实最开始就是一个 Tag 接口,但是写得很麻烦
	* 为了解决麻烦,后来sun弄了个SimpleTag,没错,也是一个接口
	* 但是出现了Tag和SimpleTage后,就出现了各自为营的场景,比较尴尬
	* 后来,为了结束这个尴尬的局面,sum又折腾出了一个叫做:JspTage的接口,作为楼上俩的爸爸
	* 很显然,我们现在使用SimpleTage接口来完成自定义标签的开发
	SimpleTage接口中的方法
		doTag();							//每次执行标签都会调用该方法,void返回值
			* 核心方法,其实每次执行标签都是执行了这个方法
			* "会在其他三个方法之后,被Tomcat调用"
		getParent();						//返回JspTage
			* 获取父标签,也就是后来的爸爸-JspTag
			* <c:when>的父标签就是<c:choose>... ...能理解了吗?
			* "非生命周期标签,而且没人调用,除了我们自己调用"
		setJspBody(JspFragment jspBody);	//void返回值
			* 设置标签体
			* JspFragment
				* 是JSP的一段,其实就是标签体的内容
		setJspContext(JspContext pc);		//void返回值
			* 设置JspContext,其实就是PageContext的父亲,这个方法其实永远都是传递的PageContext
			* 标签获得了这个"一个顶九个",那他还有啥得不到的？
		setParent(JspTag parent);			//void返回值
			* 设置父标签... ...没必要去了解了,其实都是Tomcat负责调用,不是你再调用
		
		* 所有的setXxx方法都会在doTag前面被Tomcat所调用,所以在doTag中就可以使用Tomcat传递过来的这些参数,很显然你要自己写个变量"把它们保存起来"
		* 就只有getParent没人理它了,而其他仨set会在doTag之前被调用

------------------------------------
写标签类							|
------------------------------------
	* 写一个类,实现SimpleTage接口,覆写里面的方法
		* 声明仨成员变量,注意多态.然后存储set方法传递的参数
	package com.kevin.tag;
	import java.io.IOException;
	import javax.servlet.jsp.JspContext;
	import javax.servlet.jsp.JspException;
	import javax.servlet.jsp.PageContext;
	import javax.servlet.jsp.tagext.JspFragment;
	import javax.servlet.jsp.tagext.JspTag;
	import javax.servlet.jsp.tagext.SimpleTag;
	/**
	 * 自定义标签
	 * */
	public class MyTag implements SimpleTag{
		private PageContext pageContext;
		private JspFragment jspFragment;
		private JspTag jspTage;
		/**
		 * 最重点的方法,每次调用都会执行的方法,而其他的仨set方法都是属于生命周期方法.说白了,这东西是一个单例
		 * */
		public void doTag() throws JspException, IOException {
			//需要向页面进行输出,所以要从PageContext中获取
			pageContext.getOut().print("Hello Tag");
		}
		/**
		 * 这个方法一般都是我没自己调用,服务器是不会干的
		 * */
		public JspTag getParent() {
			return jspTage;
		}
		/**
		 * 在doTage之前被服务器调用
		 * */
		public void setJspBody(JspFragment fragment) {
			//收下服务器送来的"小礼物"
			this.jspFragment = fragment;
		}
		/**
		 * 在doTage之前被服务器调用
		 * */
		public void setJspContext(JspContext context) {
			//收下服务器送来的"小礼物"
			this.pageContext = (PageContext) context;
		}
		/**
		 * 在doTage之前被服务器调用
		 * */
		public void setParent(JspTag jspTage) {
			//收下服务器送来的"小礼物"
			this.jspTage = jspTage;
		}
	}

------------------------------------
tld文件								|
------------------------------------
	* 写tld文件,一般都放在/WEB-INF目录下,自己创建一个tlds文件夹中
	* 如果说找不到约束之类的东西,就去C标签的里面去借一下吧
		<?xml version="1.0" encoding="UTF-8" ?>
		<taglib xmlns="http://java.sun.com/xml/ns/javaee"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-jsptaglibrary_2_1.xsd"
			version="2.1">
			<!-- 必须标签,自定义的版本号 -->
			<tlib-version>1.0</tlib-version>
			<!-- 简短名称,写个简称 -->
			<short-name>kevin</short-name>
			<!-- 瞎写...呵呵 -->
			<uri>http://www.kevinblandy.com</uri>
			<!-- 重点,一个tld文件中可以写多个tag,每个tag代表一个标签 -->
			<tag>
				<!-- 标签名称,必写 -->
				<name>demo</name>
				<!-- 标签处理类,很显然就是类路径了 -->
				<tag-class>com.kevin.tag.MyTag</tag-class>
				<!-- 标签体类型,如果没有标签体(空标签类似于<br/>,<hr/>),那很显然就是empty了 -->
				<body-content>empty</body-content>
			</tag>
		</taglib>
------------------------------------
页面中指定tld文件位置				|
------------------------------------
	* 导入标签库,其实就是指定tld文件的位置
	* <%@ taglib prefix="my" uri="/WEB-INF/tlds/demo.tld"%>
	* 使用:<my:demo/>    就会输出:Hello Tag


--------------------------------
SimpleTagSupport				|
--------------------------------
	* 折腾完了入门,其实作为有经验的开发人员也该想到
	* 实现接口其实也不大方便,例如:实现Servlet,后来又继承HttpServlet...
	* 所以为了更加的方便,又出现了 SimpleTagSupport
	* SimpleTagSupport
		* 这东西实现了SimpleTag接口,而且帮我们声明了成员变量存好了所有的东西
		* 就是把Tomcat传递的所有数据都保存起来了,而且还提供了get方法,给子类调用
		* 我们只需要去重写doTag就OK了
	* tld还是正常的配置就OK
		* 只需要在原来的tld文件中添加一个新的<tag>标签,正常的配置就OK
		* 一个tld文件,可以有多个<tag>每个tag都是对应着一个类



* 实际的开发中,可以在标签类中获取:"SpringUtils",来获取指定的Bean,通过Bean组键和页面传递进来的值进行逻辑操作
