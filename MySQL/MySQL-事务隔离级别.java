1,读未提交		Read uncommitted
	* 读取到其他事务未提交的数据

2,读已提交		Read committed(Oracle,SqlServer默认)
	* 只能读取到其他事务已经提交的数据
	* 能够解决脏读问题
	* 存在不可重复读问题
		* A 开始读取事务 ,读取 = 10
		* B 开始UPDATE事务,修改 = 15 提交
		* A 再次读取 = 15		
		* A 在同一事务中的两次读取的值不一样
	* 存在幻读问题

3,可重复读		Repeatable read(mysql 默认)
	* 同一读取事务中,多次读取,该值始终一样,不会因为其他事务进行了修改提交而已改变
	* 可以解决不可重复读问题
		* 不可重复读对应的是修改,即UPDATE操作
	* 存在幻读问题
		* 幻读问题对应的是插入INSERT操作,而不是UPDATE操作

4,序列化		Serializable
	* 能处理任何问题
	* 性能最差
	

------------
脏读
	* 读取到其他事务未提交的问题

不可重复读
	* 同一事务内,两次读取数据不一致

幻读
	* 读取到其他事务提交的数据
	* A 开始了更新事务,对表中的所有数据进行修改
	* B 开始了事务,'插入'了一条新的数据,提交
	* A 提交事务后,发现有一数据还未修改('B事务新插入的'),就像是产生的幻觉一样
	---------------
	* A 开启读取事务,检索符合条件的记录
	* B 开始了事务,'插入'了一条新的数据,提交
	* A 再次检索,多出了一条符合条件的记录('B事务新插入的')

------------
1、SQL规范所规定的标准，不同的数据库具体的实现可能会有些差异
2、mysql中默认事务隔离级别是可重复读时并不会锁住读取到的行
3、事务隔离级别为读提交时，写数据只会锁住相应的行
4、事务隔离级别为可重复读时，如果有索引（包括主键索引）的时候，以索引列为条件更新数据，会存在间隙锁间隙锁、行锁、下一键锁的问题，从而锁住一些行；如果没有索引，更新数据时会锁住整张表。
5、事务隔离级别为串行化时，读写数据都会锁住整张表
6、隔离级别越高，越能保证数据的完整性和一致性，但是对并发性能的影响也越大，鱼和熊掌不可兼得啊。对于多数应用程序，可以优先考虑把数据库系统的隔离级别设为Read Committed，它能够避免脏读取，而且具有较好的并发性能。尽管它会导致不可重复读、幻读这些并发问题，在可能出现这类问题的个别场合，可以由应用程序采用悲观锁或乐观锁来控制。



lock in share mode
	* 适用于两张表存在业务关系时的一致性要求
	* 场景,两张表
		parent
			paren_id
			child_id

		child
			child_id
		
		* 直接 insert 一条 child_id=100 记录到child表是存在风险的
		* 因为刚 insert 的时候可能在 parent 表里删除了这条 child_id=100 的记录,那么业务数据就存在不一致的风险
		* 正确的打开方式

			select * from parent where child_id = 100 lock in share mode;
			insert into child(child_id) values(100);

for  update
	* 适用于操作同一张表时的一致性要求
	* for update的加锁方式无非是比lock in share mode的方式多阻塞了select...lock in share mode的查询方式,并不会阻塞快照读


打个比方，你有个账户。里面存着你的钱。你现在有100块钱。
有两个人同时往你账户打钱

1，用户 A 开启了一个了事务
	select money from account where name = yang;
	money = 100

	用户A给你转50块
	update money = money + 50 where name = yang;

2，用户 B 开启了一个事务
	select money from account where name = yang;
	money = 100

	用户B给你转100块钱
	update money = money + 100 where name = yang;

3，用户B提交事务
	money = 
4，用户A提交事务
    
