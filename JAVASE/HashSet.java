HashCode
	底层数据结构是哈希表
	HashSet是如何保证元素的唯一性呢？
是通过元素的两个方法！hashCode 和 equals来完成的。
如果元素的HashCode值相同。才会判断equals是否为true。
如果元素的hashcode的值不同。不会调用equals。
