-----------------------
Redis-sortSet操作		|
-----------------------
	* sort,排序.顾名思义!这东西是一个可以排序的集合
	* 跟Set一样,也是string类型的元素集合
	* 不同的是,每个元素都会关联一个'权'
	* 通过'权值',可以有序的获取集合中的元素
	

	zadd key score member
		* 添加元素到集合,元素在集合中存在则更新对应score
		* score,也就是用来排序的值,传说中的权重
		* 允许相同值的存在,但是不允许member相同,已经存在会被替换掉
	zrem key member 
		* 删除指定元素,1.表示删除成功,如果元素不存在返回0
	zincrby key incr member
		* 按照incr幅度增加对应member的score值,返回score值
	zrank key member
		* 返沪指定元素在集合中的排名(下标),集合元素是按从小到达排序的
	zrevrank key member
		* 同上但此时的集合确实从大到小排序的
	zrange key start end
		* 类似lrange 操作从集合中指定区间的元素,返回的是有序结果
		* 注意,这个开始结束值是下标
	zrevrange key start end
		* 同上,返回结果是逆序的
	zcard key
		* 返回集合中元素的个数
	zscore key element
		* 返回给定元素对应的score 
	zremrangebyrank key min max
		* 删除集合中,指定下标的元素,集合是从小到大排序的、
		* 且包含开始,也包含结束
	

	member,可以理解为value