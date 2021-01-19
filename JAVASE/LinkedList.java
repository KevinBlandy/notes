LinkedList	容器
底层使用的是链表数据结构
特点：
	查找慢，但是增删快！
特有方法：
addFIrst();
	把元素放在容器第一个位置。后来居上！！
addLast();
	把元素放在容器最后的位置。后来居后！！

getFirst();
getLast();
	获取元素。但是不删除元素
removeFirst();
removeLast();
	获取元素。但是会删除元素。如果集合中没有元素会出现--异常！

在JDK1.6出现了替代方法！
offerFirst();
offerLast();
	添加元素。同上
peekFirst();
peekLast();
	获取元素，但不删除同上
pollFirst();
pollLast();
 	
	获取元素。但是会删除元素。如果集合中没有元素会返回 ——null!



------------------
	
	用户id
	模块  
	操作