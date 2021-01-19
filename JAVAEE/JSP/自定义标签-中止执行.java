----------------------------
不执行标签下面的页面内容	|
----------------------------
	* 其实就是说,希望执行了指定的标签后,不再执行标签下面的内容,那么就可以使用一个异常
	* SkipPageException
		package com.kevin.tag;
		import java.io.IOException;
		import javax.servlet.jsp.JspException;
		import javax.servlet.jsp.SkipPageException;
		import javax.servlet.jsp.tagext.SimpleTagSupport;
		/**
		 * 中止执行
		 * */
		public class MyTagB extends SimpleTagSupport{
			public void doTag() throws JspException, IOException {
				this.getJspContext().getOut().write("只能看到我,后木有了");
				/*
					这个异常不会抛出来的,Tomcat会捕获它
					Tomcat认识它
				*/
				throw new SkipPageException("后面不执行了");
			}
		}
	* 其实这东西去翻下JSP真身,就可以看懂原理了
	* 一个try catch .当异常发生,后面的就不用执行了

	