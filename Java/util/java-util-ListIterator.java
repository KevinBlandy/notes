--------------------
ListIterator		|
--------------------
	# Iterator 的子类
	# 可以实现更强悍的迭代
	# 正序/逆序迭代

--------------------
实例方法			|
--------------------
	void add(E e) 
		将指定的元素插入列表（可选操作）。 

	boolean hasNext() 
		以正向遍历列表时，如果列表迭代器有多个元素，则返回 true（换句话说，如果 next 返回一个元素而不是抛出异常，则返回 true）。 

	boolean hasPrevious() 
		如果以逆向遍历列表，列表迭代器有多个元素，则返回 true。 

	E next() 
		返回列表中的下一个元素。 

	int nextIndex() 
		返回对 next 的后续调用所返回元素的索引。 

	E previous() 
		返回列表中的前一个元素。 

	int previousIndex() 
		返回对 previous 的后续调用所返回元素的索引。 

	void remove() 
		从列表中移除由 next 或 previous 返回的最后一个元素（可选操作）。 

	void set(E e) 
		用指定元素替换 next 或 previous 返回的最后一个元素（可选操作）。 
