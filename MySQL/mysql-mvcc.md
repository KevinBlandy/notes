# MVCC 和 事务隔离级别
## 事务并发可能遇到的问题
- 脏写（Dirty Write），一个事务修改了另一个未提交事务修改过的数据
- 脏读（Dirty Read），个事务读到了另一个未提交事务修改过的数据
- 不可重复读（Non-Repeatable Read）一个事务只能读到另一个已经提交的事务修改过的数据，并且其他事务每对该数据进行一次修改并提交后，该事务都能查询得到最新值
- 幻读（Phantom）一个事务先根据某些条件查询出一些记录，之后另一个事务又向表中插入了符合这些条件的记录，原先的事务再次按照该条件查询时，能把另一个事务插入的记录也读出来。

- 严重性：脏写 > 脏读 > 不可重复读 > 幻读

## 四种隔离级别
- READ UNCOMMITTED：未提交读。可能发生脏读、不可重复读和幻读问题。
- READ COMMITTED：已提交读。可能发生不可重复读和幻读问题，但是不可以发生脏读问题。
- REPEATABLE READ：可重复读。能发生幻读问题，但是不可以发生脏读和不可重复读的问题.（MYSQL默认）
- SERIALIZABLE：可串行化。各种问题都不可以发生。
- 脏写的问题
    - 脏写这个问题太严重了，不论是哪种隔离级别，都不允许脏写的情况发生。
    - InnoDB使用锁来保证不会有脏写情况的发生，也就是在第一个事务更新了某条记录后，就会给这条记录加锁，另一个事务再次更新时就需要等待第一个事务提交了

```
隔离级别	            脏读	            不可重复读	    幻读
READ UNCOMMITTED	Possible	    Possible	    Possible
READ COMMITTED	    Not Possible	Possible	    Possible
REPEATABLE READ	    Not Possible	Not Possible	Possible
SERIALIZABLE	    Not Possible	Not Possible	Not Possible
```

## MVCC原理
- InnoDB存储引擎的表来说，它的聚簇索引记录中都包含两个必要的隐藏列
    - trx_id：每次一个事务对某条聚簇索引记录进行改动时，都会把该事务的事务id赋值给trx_id隐藏列。
    - roll_pointer：每次对某条聚簇索引记录进行改动时，都会把旧的版本写入到undo日志中，然后这个隐藏列就相当于一个指针，可以通过它来找到该记录修改前的信息。

### 版本链
- 每次对记录进行改动，都会记录一条undo日志，每条undo日志也都有一个roll_pointer属性
- INSERT操作对应的undo日志没有该属性，因为该记录并没有更早的版本
- 将这些undo日志都连起来，串成一个链表,这个链表称之为版本链，版本链的头节点就是当前记录最新的值。另外，每个版本中还包含生成该版本时对应的事务id

### ReadView
- 为了判断一下版本链中的哪个版本是当前事务可见的。为此，设计InnoDB的大叔提出了一个ReadView的概念
- ReadView中主要包含4个比较重要的内容
    - m_ids：表示在生成ReadView时当前系统中活跃的读写事务的事务id列表。(不包括自己的事务ID)
    - min_trx_id：表示在生成ReadView时当前系统中活跃的读写事务中最小的事务id，也就是m_ids中的最小值。
    - max_trx_id：表示生成ReadView时系统中应该分配给下一个事务的id值。（也就是m_ids中的最大值 + 1 ？？）
    - creator_trx_id：表示生成该ReadView的事务的事务id。
> 事务id是递增分配的。比方说现在有id为1，2，3这三个事务，之后id为3的事务提交了。那么一个新的读事务在生成ReadView时，m_ids就包括1和2，min_trx_id的值就是1，max_trx_id的值就是4。

- 有了ReadView，这样在访问某条记录时，只需要按照下边的步骤判断记录的某个版本是否可见：
    - 如果被访问版本的trx_id属性值与ReadView中的creator_trx_id值相同，意味着当前事务在访问它自己修改过的记录，所以该版本可以被当前事务访问。
    - 如果被访问版本的trx_id属性值小于ReadView中的min_trx_id值，表明生成该版本的事务在当前事务生成ReadView前已经提交，所以该版本可以被当前事务访问。
    - 如果被访问版本的trx_id属性值大于或等于ReadView中的max_trx_id值，表明生成该版本的事务在当前事务生成ReadView后才开启，所以该版本不可以被当前事务访问。
    - 如果被访问版本的trx_id属性值在ReadView的min_trx_id和max_trx_id之间，那就需要判断一下trx_id属性值是不是在m_ids列表中，如果在，说明创建ReadView时生成该版本的事务还是活跃的，该版本不可以被访问；如果不在，说明创建ReadView时生成该版本的事务已经被提交，该版本可以被访问。
    
> 如果某个版本的数据对当前事务不可见的话，那就顺着版本链找到下一个版本的数据，继续按照上边的步骤判断可见性，依此类推，直到版本链中的最后一个版本。如果最后一个版本也不可见的话，那么就意味着该条记录对该事务完全不可见，查询结果就不包含该记录。

- READ COMMITTED和REPEATABLE READ隔离级别的的一个非常大的区别就是它们生成ReadView的时机不同
    - READ COMMITTED —— 每次读取数据前都生成一个ReadView
    - REPEATABLE READ —— 在第一次读取数据时生成一个ReadView

### Purge线程
- insert undo在事务提交之后就可以被释放掉了，而update undo由于还需要支持MVCC，不能立即删除掉。
- 为了支持MVCC，对于delete mark操作来说，仅仅是在记录上打一个删除标记，并没有真正将它删除掉。
- 随着系统的运行，在确定系统中包含最早产生的那个ReadView的事务不会再访问某些update undo日志以及被打了删除标记的记录后，有一个后台运行的purge线程会把它们真正的删除掉。

  
### 总结
- 所谓的MVCC（Multi-Version Concurrency Control ，多版本并发控制）指的就是在使用READ COMMITTD、REPEATABLE READ这两种隔离级别的事务在执行普通的SELECT操作时访问记录的版本链的过程，这样子可以使不同事务的读-写、写-读操作并发执行，从而提升系统性能。
- READ COMMITTD、REPEATABLE READ这两个隔离级别的一个很大不同就是：生成ReadView的时机不同，READ COMMITTD在每一次进行普通SELECT操作前都会生成一个ReadView，而REPEATABLE READ只在第一次进行普通SELECT操作前生成一个ReadView，之后的查询操作都重复使用这个ReadView就好了。