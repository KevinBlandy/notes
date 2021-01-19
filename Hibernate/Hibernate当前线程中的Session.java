————————————————————————————
一,Hibernat当前线程的Session|
————————————————————————————
	> 事务通常会在Service层次开启,但是Session在DAO层!事务的开启是由Session来开启的!
	
	getCurrentSession();				//获取与当前线程绑定的Session
		* 使用这个需要在主配置文件中配置！
		* <property name="current_session_context_class">thread</property>
		* 如果线程没有绑定,那么执行这个就立即绑定一个新的并且返回!

	如果把Hibernate配置文件中
	current_session_context_class的属性设置为:thread
	那么Hibernate就会按照与本地线程绑定的方式来管理Session

	Hibernate按一下规则吧Session与本地线程绑定
	* 当一个线程第一次调用SessionFactory对象的getCurrentSession()的时候,该方法会创建一个新的Session对象
	  把该对象与线程绑定,并且返回这个Session
	 
	* 当再次调用SessionFactoy的getCurrentSession()的时候,就返回这个已经绑定的Session

	* 当事务提交的时候,Hibernate会自动清理Session对象的缓存,然后提交事务!并且关闭Session,当撤销关联的事务的时候,也会自动关闭Session

	* 如果再次调用SessionFactory的getCurrentSession()的时候,又会创建一个新的Session与线程绑定,并且返回! 

————————————————————————————
二,Session的管理			|
————————————————————————————
	尽管让程序自主管理Session对象的生命周期,也是可行的!但是实际的应用中把管理Session对象的生命周期交给Hibernate管理,可以简化java应用程序代码和软件架构

	Hibernate3自身提供了三种管理Session对象的方法

		1,session 对象的声明周期与本地线程绑定
		2,session 对象的声明周期与JTA事务绑定
		3,Hibernate 委托程序管理Session 对象的生命周期

	在Hibernate的配置文件中
		current_session_context_class属性用于指定Session的管理方式
	
	thread: Session对象的生命周期与本地线程绑定
	jta*  : Session对象的生命周期余额JTA事务绑定
	managed: Hibernate委托程序来管理Session对象的生命周期

	'注意'
		绑定当前线程中的Session不需要关闭.线程会帮我们自动的关闭!不然会报错!
————————————————————————————
三,旧笔记	(经典)			|
————————————————————————————
在应用程序中使用Session的管理方案

一个业务操作,是一个事务,一个请求,一个事务!一个请求也就是调用一个业务方法!

把事务,搬到业务层!但是,这个东西不能直接把代码搬过去!持久层的代码不能出现在业务层!
-------------------------------这种方法好理解,在没有学会动态代理之前.可以用这个!别忘了催希凡老师讲过的哟!TxQueryRunner....
//工具类
class Utils
{
	public  static final ThreadLocal<Session> threadLoacl = new ThreadLocal<Session>();
}
//过滤器
class MyFilter implements Fileter
{
	public void doFilter(request,response,chain)
	{
		Session session = sessionFactory.openSession();
		Utils.threadLoacl.set(session);//把Session存入线程容器,绑定到当前线程
		try
		{	session.beginTransaction();	//开启事务
			chain.doFilter();		//放行
			session.getTransaction().commit();//提交事务
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();	//异常出现,事务回滚
			thorw new RuntimeException(e);
		}
		finally
		{
			session.close();
			Utils.threadLoacl.remove();//移除当前线程中的Session,解除绑定
		}
	}
}

//那么DAO中的代码就是:
public class UserDao
{
	public void saveUser(User user)
	{
		Session sessiom = Utils.threadLoacl.get();//从线程容器中获取当前线程的Session对象
		session.save(user);
	}
}
总结:使用ThreadLocal容器,使用当前线程来作为Key,这样的话保证了并发方法的安全,以及过滤器和DAO中的Session是同一个!
-------------------------------------这种方法,更叼,可以利用Hibernate的API来完成!
//获取当前绑定的Session
Session session = sessionFactory.currentSession();
	> 需要在主配置的xml文件中添加,不然会报错
	> <property name="current_session_context_class">thread</property>
作用:去指定的上下文中(如线程Thread),查找绑定的Session对象,如果有就返回,如果没有就创建一个并绑定好再返回
> 上面配置文件中的thread,你就应该懂了!
> oppenSession(),只是开启一个新的Session,不会做绑定和查找操作!
> 提交事务/回滚后,还会自动的关闭Session,移除与线程的绑定,而且使用这个方法的时候,不能再自己关闭Session,会报错!别人已经帮你关闭了,你又给关一次!

			Session session1 = sessionFactory.getCurrentSession();
			Session session2 = sessionFactory.getCurrentSession();
			System.out.println(session1==session2);
			---结果是:true
			Session session1 = sessionFactory.openSession();
			Session session2 = sessionFactory.getCurrentSession();
			System.out.println(session1==session2);
---结果是:false
//过滤器
class MyFilter implements Fileter
{
	public void doFilter(request,response,chain)
	{
		Session session = sessionFactory.getCurrentSession();//绑定,Session
		try
		{	session.beginTransaction();	//开启事务
			chain.doFilter();		//放行
			session.getTransaction().commit();//提交事务
		}
		catch(Exception e)
		{
			session.getTransaction().rollback();	//异常出现,事务回滚
			thorw new RuntimeException(e);
		}
	}
}
//DAO
public class UserDao
{
	public void saveUser(User user)
	{
		Session sessiom = Session session = sessionFactory.getCurrentSession();//获取当前绑定的Session
		session.save(user);
		close();
	}
}
总结:依托于SessionFactory的Session session = sessionFactory.getCurrentSession();方法,来获取绑定的对象!从而达到,从过滤器到Dao都是一个Session!
> 这个方法一定要在主配置文件中加上标签啊!
	
