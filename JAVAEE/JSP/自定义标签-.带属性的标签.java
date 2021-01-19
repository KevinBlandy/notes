----------------------------
带属性的自定义标签			|
----------------------------
	* 这个相对来说,有点复杂.
	* <c:if test=""></c:if>
		* test就是一个属性
	* 我们自定义一个跟if一样的自定义属性标签
	步骤
		1,给标签处理类添加属性
		2,在tld文件中属性进行部署(描述,配置)

----------------------------
带属性的自定义标签类		|
----------------------------
package com.kevin.tag;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * 带属性的标签
 * */
public class MyTagC extends SimpleTagSupport{
	/**
	 * 标签要有属性,那么类就有属性.注意,属性是get/set
	 * 其实仅仅提供set就好了,get都不用提供
	 * */
	private boolean test;
	public void doTag() throws JspException, IOException {
		//获取标签体
		JspFragment jspFragment = this.getJspBody();
		//可以根据页面传递的数据来进行逻辑操作,如果是true,那么我就执行标签体的内容,不然就不鸟
		if(test){
			/*
		  		如果传递的输出流是null,表示使用的就是当前页面的out
			*/
			jspFragment.invoke(null);
		}
	}
	public boolean isTest() {
		return test;
	}
	/**
	 * 会由tomcat调用,会在doTag之前执行,所以我没就可以使用该属性
	 * */
	public void setTest(boolean test) {
		this.test = test;
	}
}


----------------------------
带属性的自定义标签tld		|
----------------------------
	<tag>
		<name>democ</name>
		<tag-class>com.kevin.tag.MyTagC</tag-class>
		<body-content>scriptless</body-content>
		<!-- 部署属性 -->
		<attribute>
			<!-- 属性名 -->
			<name>test</name>
			<!-- 该属性是否必须给出 -->
			<required>true</required>
			<!--
				runtime expretion value
				也就是运行时表达式值,就是说:这个属性赋的值是否为表达式
				一般都是true,不然就失去意义了.if(fasle)直接写不是自己逗自己玩嘛
			-->
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
	* attribute属性可以有多个,代表着有多个属性
----------------------------
带属性的自定义标签使用		|
----------------------------
	* 导入就不说了
		<body>
			<%
				//模拟赋值
				request.setAttribute("flag", false);
			%>
			<div>
				<my:democ test="${flag }">
					<!-- 值为true,那么这个true就会执行 -->
					true
				</my:democ>
			</div>
		</body>
	