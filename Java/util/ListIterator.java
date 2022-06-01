---------------
ListIterator
---------------
	# public interface ListIterator<E> extends Iterator

	# 此迭代器可以在迭代过程中修改,删除,插入元素
		* 特别适合链表结构的操作

	# 抽象方法
		boolean hasNext();
		E next();
		boolean hasPrevious();
		E previous();
		int nextIndex();
		int previousIndex();
		void remove();
		void set(E e);
		void add(E e);
