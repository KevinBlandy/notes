SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
Session session = sessionFactory.openSession();
--------------------------------------
对象状态
在Hibernate中,java对象分为4种状态
	> Session对象特定的方式能把对象从一个状态变为另一个状态
1,临时状态
	> 未保存的对象,就是刚new出来的对象,跟数据库没关系,没对应.跟Session也没关系
2,持久化状态
	> 对象在Session的管理之中,最终会有对应的数据库记录
	* 1,有主键,OID!对对象的修改,会同步到数据库中!
3,游离状态
	> 数据库中有对应记录,但是对象已经不由Session管理,操作它,不会影响数据库
	* 修改此状态对象的时候,数据库不会有变化!
4,删除状态
	> 这个就简单了,就是调用了session.delete();后的对象!

--------------------------------------
Session中操作对象的方法
操作实体对象的----------
save();
	> 把临时状态变,为持久化状态
	* 会生成insert into ...语句
update();
	> 把游离状态,变为持久化状态
	* 会生成insert into 或 update语句... ..
	* 在更新的时候,如果对象不存在就会报错!也就是对象中的主键在数据库中找不到就会报错
saveOrUpdate();
	* 会生成inert into 或者update 语句
	* 这个方法是根据id判断对象是什么状态的!如果id是null,那就就认为是临时状态,如果不是原始值,就是游离状态
delete(Object);
	> 删除
	* 不能是null值,而且如果Object的id在数据库中不存在(删除的对象不存在),那么就会抛出异常
操作缓存的----------
flush()
	> 把Session中管理的对象,全部刷新到数据库!
	* 马上执行SQL语句,把Session中管理的对象持久化到数据库
	* 默认是在事务提交的时候执行!事务提交的时候会先执行这个!
	* 这个方法并不会清除Session中的对象,只是执行了SQL语句
evice(Object)
	> 把指定的对象,从Session中清除.不交Session管理,那么这个对象就是一个游离状态的对象了
clear()
	> 清空Session中的所有对象
	* 这个方式并会执行SQL语句,只是清除了Session中的对象

查询实体对象的
get();
	* 会生成select ... where id=?语句
	* 返回的是原始对象
	* 在执行这个get方法的时候,先看Session缓存中有没有,如果有这个对象的话就返回,不然就就会立即执行SQL查询语句来获取对象
	* 如果不存在就返回null
load();
	* 最终也会生成select ... where id=?语句
	* 返回的是一个代理对象(要求类不能是final,不然懒加载失效,因为不能继承,也就是不能生成子类的一个代理对象)
		> 1,把实体类写成final
		> 2,映射文件中lazy="true" 
	* 懒加载,延迟加载.不会马上执行SQL语句,而是在第一次使用非id或者Class属性时才会执行SQL语句!
	* 如果不存在,就会抛出异常
refresh(Object);
	* 刷新缓存中指定对象的状态,就是重新Select一下！
createQuery(String HQL)
	> 执行HQL语句,返回Query对象
createCriteria()
	>
getNamedQuery(String name)
	> 命名查询,从配置文件中读取指定名称的HQL语句,并执行！返回Query对象
	
-----------
关于Session中的缓存问题!
	因为Session里面是缓存着一大堆的对象,save啊之类的操作进来的！在执行flush或者commit的时候才会执行插入的SQL语句!
如果说,对象过多(同时save了10W条数据),或者对象过大(对象中带有一个50MB的字节数组)!都会导致Session过大,导致内存溢出.从而抛出异常!
所以,有时候我们需要判断!在Session快要装满的时候,执行一下flush,然后再执行clear.把Session中的数据先插入到数据库中!然后在吧Session容器情况!进行下一波数据的存储

关于事务的隔离级别
读未提交			1	0001	
读已提交			2	0010
可重复读			4	0100
串行化(不可并发)	8	1000
这个东西可以在hibernate.cfg.xml中进行配置
	>  如果你需要多个级别同时存在.那就按照二进制的规则来!