											JSP指令		

**************************  page指令  **************************
<%@ page language="java" contentType="text/html" charset="utf-8" import="java.uti.Properties" %>
属性名称		取值范围		描述	
*language		java			指明解释该JSP文件时采用的语言,一般为java,默认为java.
*import			任何包.类名		引入该JSP中用到的类,包等。是唯一个可以出现多次的page指令属性
*session		boolean			指明该JSP内是否内置session对象。true则内置,可直接使用。false则不内置。默认为true
*autoFlush		boolean			是否运行缓存.true时,out.println()等方法输出字符串会先存入缓存。缓存存满或者执行										out.flush()才刷新到客户端。默认为true
*buffer			none/数字kb		指定缓存大小(默认8KB),当autoFlush为true时有效。例:<%@ page buffer="10kb" %>
*isThread		boolean			指定线程是否安全,如果为true则运行多个线程同时运行该JSP。
								否则JSP生成的servlet会去实现一个'已经过时'的标记接口SingleThreadNodel只能运行一个线程,其余线程等待。默认为false
*isErrorPage	boolean			指定该页面是否为错误处理页面,如果为true则该JSP内置一个Exception的实例对象*exception
								这个页面会设置状态码:500,该对象可以直接使用。否则没有。默认是false
								(web.xml文件中也可以指定错误页面。这种方式其实与page无关)
*errorPage		某JSP相对路径	指明一个错误显示页面,如果该JSP抛出一个未捕捉的异常,则转到errorPage指定路径。
								并且客户端不会报500错误.状态码为200状态。
								errorPage指定页面通常page指令的isErrorPage属性为true。
*contentType		有效文档类型	客户端浏览器根据该属性判断文档类型,例如
								HTML    --	text/html
								纯文本	--	text/plain
								JPG     --	image/jpeg
								GIF	    --	image/gif
								WORD    --	application/msword
*info			任意字符串		指明JSP的信息,该信息可以通过Servlet.getServletInfo();方法获取到
*trimDirective
 Whitespaces	boolean			是否去掉指令前后的空白符,默认false.该空白会影响到JSP生成的html代码顶端空格
*extends		任何包.类名		让该JSP生成的Servlet去继承指定的类
*isELgnored		boolean			是否忽略el表达式,默认值是false.即不忽略！老版本(我们是看不着了)的JSP默认值是ture。
*pageEncoding	有效字符编码	指定当前JSP页面的编码,只要跟文本也是按照这个指定格式来解析。就不会出现乱码
								服务器要把JSP编译成JAVA代码的时候,就需要使用pageEncoding	
**************************  include指令  **************************
<%@ include file="路径" %>
作用：
   把页面分解,使用包含的方式组合在一起。这样一个页面中不变的部分。就是一个独立的JSP。我们只需要处理变化的页面。
   多个JSP页面中都包含同样的导入标签。每个都有同样的，太累。那么我就可以新建一个jsp页面导入所有需要的类。再让需要导入的JSP去包含(include)这个JSP就行了。

   - 与RequestDispatcher的include();方法相似！但是略有不同
   - <%@ include %>  表示JSP编译成JAVA文件时完成的！他们共同生成一个JAVA文件(servlet),然后再生成一个class。
   - RequestDispatcher的include()是一个方法，包含和被包含的是两个Servlet,他们只是把响应的内容,在运行时合并。
* file
	  <%@include file="x.jsp">其中file就表示要包含的JSP文件。这个file的值不能是变量！因为这个东西是在编译成JAVA文件的 时候就合并成了一个Servlet！
	  如果是变量,那么变成class文件的时候才有用！如果换成,那么jsp就不认识你的表达式是指向的哪个jsp文件。因为是个变量不是具体的JSP页面！
	  而且被包含的页面要注意不能跟主页面有重复的累赘元素(只能单一存在的元素<html>,<baody>,<head>... ...)

   ------总结
	语法：<%@include file="页面"%>
	include指令的作用是包含指定的页面！在jsp被编译成java文件之前会把两个jsp文件合并，然后再编译成一个java文件。

	注意：
	<%@include file="<%=myfile%>" %>
	这是不能通过编译的，因为myfile是一个变量，它的值只有在java编译成class后执行时才能确定。而include指令需要在jsp编译java时就要确定包含的是哪个页面，所以...

**************************  taglib指令  **************************
<%@ tiglib uri="http://java.sum.com/jsp/jstl/core" prefix="c" %>
* prefix		任意字符串		指定标签库在本页面中的前缀,防止标签重命名
* uri			已知标签库位置	获取这个库的标签,可以使用




**************************  总结  **************************
一个JSP页面可以中有0-N个指令的定义.
JSP指令其实不需要放在第一行,但是现在放在第一行已经成了规范。并不代表只能放在第一行

在WEB.xml文件中配置
<jsp-config>
	<jsp-property-group>
		<url-pattern>*.jsp</url-pattern> <!--表示对所有jsp进行配置-->
		<el-ignored>true</el-ignored> <!--忽略EL表达式-->
		<page-encoding>UTF-8</page-encoding> <!--编码为utf-8-->
		<scripting-invalid>true</scripting-invalid> <!--禁用java脚本-->
	</jsp-property-group>
</jsp-config>