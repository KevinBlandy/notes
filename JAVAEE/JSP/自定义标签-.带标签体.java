-----------------------
带标签体的标签			|
-----------------------
	* 很显然,你必须要是基础入门已经看懂了.OK你可以进阶的修炼
	* 其实就是tld中body-content要配置的东西
		<tag>
			<!-- 标签名称,必写 -->
			<name>demo</name>
			<!-- 标签处理类,很显然就是类路径了 -->
			<tag-class>com.kevin.tag.MyTag</tag-class>
			<!-- 标签体类型,如果没有标签体(空标签类似于<br/>,<hr/>),那很显然就是empty了 -->
			<body-content>empty</body-content>
		</tag>
	* body-content,中可以配置四种值
		1,empty
			* 也就是传说中的无标签体
		2,JSP
			* 传统标签支持它,但是SimpleTag(jsp2.0)已经不再支持
			* 标签体的内容可以是任何东西:EL,JSL,<%=%>,<%%>甚至是html
		3,scriptless
			* 专门为jsp2.0提供的.标签体的内容不能是java标本,但可以是EL,JSTL等
			* '在SimpleTag中,如果需要有标签体,那么就使用它'
		4,tagdependent
			* 标签体内容不做运算,由标签处理类自行处理,无论标签体是EL,JSP,JSTL都不会做运算
			* 这个选项挤几乎没人会使用
		* 其实要么选1,要么就选3
-----------------------
带标签体标签类			|
-----------------------
	package com.kevin.tag;
	import java.io.IOException;
	import java.io.Writer;
	import javax.servlet.jsp.JspException;
	import javax.servlet.jsp.tagext.JspFragment;
	import javax.servlet.jsp.tagext.SimpleTagSupport;
	/**
	 * 带标签体的标签
	 * */
	public class MyTagB extends SimpleTagSupport{
		public void doTag() throws JspException, IOException {
			//获取当前jsp页面的输出流
			Writer out = this.getJspContext().getOut();
			//在实际的el表达式输出之前输出点东西
			out.write("******一堆星星<br/>");
			//获取到标签体
			JspFragment jspFrament = this.getJspBody();
			/*
				执行标签体内容(其实一般都是解析el表达式),把结果直接写入指定的流中
				如果标签传入的直接是个字符,那么会原样输出.
				<my:demob>${name}</my:demob>	--> 会被解析后输出
				<my:demob>Kevin</my:demob>		--> 原样输出
			*/
			jspFrament.invoke(out);
			//在实际的el表达式输出之后输出点东西
			out.write("<br/>******一堆星星");
		}
	}
	'果传递的输出流是null,表示使用的就是当前页面的out'
	* jspFrament.invoke(null);		//直接可以这么写跟里面写out是一样的其实

-----------------------
带标签体tld文件			|
-----------------------

	<tag>
		<name>demob</name>
		<tag-class>com.kevin.tag.MyTagB</tag-class>
		<!-- 注意是带标签体的 -->
		<body-content>scriptless</body-content>
	</tag>

-----------------------
带标签体标签使用		|
-----------------------
	* 导入
		<%@ taglib prefix="my" uri="/WEB-INF/tlds/demo.tld"%>
	* 使用
		<%
			//模拟数据,存入request域
			request.setAttribute("name", "KevinBlandy");
		%>
		<body>
			<h1><my:demoa/></h1>
			<h1><my:demo/></h1>
			<div>
				<!-- ${name},就是标签体 -->
				<my:demob>${name }</my:demob>
			</div>
		</body>
	* 本例输出结果:
		******一堆星星
		KevinBlandy
		******一堆星星
