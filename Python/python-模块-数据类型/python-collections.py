-------------------------------
collections						|
-------------------------------
	* 该模块,提供了许多有用的集合类

-------------------------------
collections-属性				|
-------------------------------
-------------------------------
collections-函数				|
-------------------------------

-------------------------------
collections-namedtuple			|
-------------------------------
	* 一个可以有名字的 tuple 数据类型
	* namedtuple 是一个函数,它用来创建一个自定义的 tuple 对象,并且规定了tuple元素的个数,并可以用属性而不是索引来引用tuple的某个元素
	* 这样一来,用 namedtuple 可以很方便地定义一种数据类型,它具备tuple的不变性又可以根据属性来引用,使用十分方便
	* demo
		from collections import namedtuple

		Point = namedtuple('Point', ['x', 'y'])
		p = Point(1, 2)

		print(p.x)		# 1
		print(p.y)		# 2

		isinstance(p, Point)	# True
		isinstance(p, tuple)	# True
	
-------------------------------
collections-deque				|
-------------------------------
	* 使用 list 存储数据时,按索引访问元素很快,但是插入和删除元素就很慢了
	* 因为 list 是线性(数组)存储,数据量大的时候,插入和删除效率很低
	* deque 是为了高效实现插入和删除操作的双向列表(链表),适合用于队列和栈
	* 创建实例
		deque(seri)
	
	* 实例方法
		append()
			* 添加数据到结尾

		appendleft()
			* 添加数据到最前面
		
		pop()
			* 删除最后一个数据,并且返回
		
		popleft()

			* 删除最前面一个元素,并且返回


-------------------------------
collections-defaultdict			|
-------------------------------
	* 具备默认值钩子函数的 dict
	* 创建实例
		defaultdict(func)
			* 当访问的key不存在时,就会返回 func() 的返回值
	
	* api跟 dict 都是一样的,只是在访问不存在的key,就会返回钩子函数的返回值
		dic = defaultdict(lambda : '没有哟')
		dic['name'] = 'Kevin'
		print(dic['name'])	# Kevin
		print(dic['age'])	# 没有哟

-------------------------------
collections-OrderedDict			|
-------------------------------
	* 普通 dict 是无序的
	* 该 dict 是有序的 dict
	* OrderedDict 的Key会按照插入的顺序排列,不是Key本身排序(继承与Dict)
	* 创建
		collections.OrderedDict([('b', 2),('a', 1) , ('c', 3)])
	
	* demo
		import collections
		dic = dict([('b', 2),('a', 1), ('c', 3)])
		print(dic)
		dic = collections.OrderedDict([('b', 2),('a', 1) , ('c', 3)])
		print(dic)
	
	* api
		tuple popitem(last = True)
			* 删除元素,并且返回
			* 关键字参数,是否是删除最后一个元素
				last	# 默认值:True
		
		 None move_to_end(key, last=True)
			*  移除元素到最后/前 ,前后取决于参数:last

	
	* 可以实现一个 FIFO(先进先出)的 dict,当容量超出限制时先删除最早添加的Key
		



-------------------------------
collections-Counter				|
-------------------------------
	* 简单的统计容器
	* Counter 实际上也是dict的一个子类
	* 创建
		c = Counter()

	* demo
		from collections import Counter
		c = Counter()
		for ch in 'programming':
			c[ch] = c[ch] + 1

		print(c)
		# Counter({'r': 2, 'g': 2, 'm': 2, 'p': 1, 'o': 1, 'a': 1, 'i': 1, 'n': 1})
