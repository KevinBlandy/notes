————————————————————————————————
一,	模型驱动					|
————————————————————————————————
	关于模型驱动,struts2中很多东西都围绕这个在说!
	<interceptor name="modelDriven" class="com.opensymphony.xwork2.interceptor.ModelDrivenInterceptor"/>
	* 框架中很多基于模型驱动的东西,都是这个东西做的
	> 下面是有他组成的一些拦截器桟
	<!-- Sample model-driven stack  -->
    <interceptor-stack name="modelDrivenStack">
    <interceptor-ref name="modelDriven"/>
    <interceptor-ref name="basicStack"/>
    </interceptor-stack>

————————————————————————————————
二,	自己实现模型驱动			|
————————————————————————————————
接口
public interface MyDomelDriven<T>
{
	public T getModel();
}
Action
public class ActionDemo implements MyDomelDriven<User>
{
	private User user = new User();
	public User getModel()
	{
		return user;
	}
}

在拦截器/过滤器中判断
if (action instanceof MyDomelDriven)
{
	。。
}


"其实,就是在过滤器中判断是不是指定接口的子类,如果是调用接口方法就是了"
"然后再进行赋值"

	