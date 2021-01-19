-----------------------
Redis-List链表操作		|
-----------------------
	* List 链表类型
	* 其实跟 LinkedList 异曲同工

	lpush key string
		* 在key对应的list头部添加字符串元素

	rpush key string
		* 在key对应的list尾部添加字符串元素

	lpop key
		* 从list头部删除元素

	rpop key
		* 从list尾部删除元素

	llen key
		* 返回list的长度,key不存在返回0,key对应的类型不是list会报错

	lrange key start end
		* 返回指定区间内的元素,下标从0开始

	ltrim key start end
		* 截取list,仅保留指定区间内元素