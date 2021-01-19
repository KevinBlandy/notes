有时候一个请求需要多个Servlet协作才能完成。所以需要在一个Servlet跳到另一个Servlet上！
	> 一个请求跨多个servlet,都需要使用转发和包含
	> 请求转发:由下一个servlet完成响应体!当前servlet可以设置响应头。设置响应体没用,还有可能报错.留头不留体
	> 请求包含:由两个servlet共同完成响应体!(都留)
	> 无论是请求还是包含,都在一个请求范围内.使用同一个request和response.

RequestDispatcher dispatcher = request.getRequestDispatcher("uri");
dispatcher.forward(request,response);//请求转向,
dispatcher.include(request,response);// 请求转发,
	|--转向是通过:RequestDispatcher 对象的 forward方法来完成的
	|--这种转向，是由WEB服务器完成。只能转向该WEB资源内部的Servlet。而且客户端浏览器地址栏会一直停滞在第一次访问servlet的地址。
	|--在执行forward动作的时候不要有任何输出到客户端。否则会抛出IllegaState-Exception.也就是在forward之前尽量不要使用out.println();语句向客户端输出结果。
request域
	三大域对象(request,session,application)
	都有如下三个操作属性的方法
setAttribute(String name,Object value);//void.无返回值类型！
getAttribute(String name); //返回值是Object.
removeArribute(String name);//返回值void.移除指定的属性
[request -- 参数是浏览器发给服务器的,属性。是servlet之间传递值用的]
> 同一请求范围内使用request.setAttribute(),request.getAttribute()来传值！
> 前一个servlet调用setAttribute()保存值。后一个servlet调用getAttribute()得到值！

请求转发和重定向(Redirect)的区别
 > 请求转发是一个请求一次响应.而重定向是两次请求两次响应.
 > 请求转发,客户端地址栏不会发生变化.而重定向会显示后一个请求的地址.
 > 请求转发只能转发到本项目其他servlet,而重定向不只能重定到本项目其他servlet还能定向到其他项目.
 > 请求转发是服务端行为,只需要给出转发的Servlet路径,而重定向需要给出requestURI,即包含项目名.
	<>需要地址栏发生变化,那么必须使用重定向
	<>需要在下一个serlvet中获取request域中的数据,必须要使用转发.