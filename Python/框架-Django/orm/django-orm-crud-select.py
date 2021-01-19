
------------------------
ROM检索					|
------------------------
	* 检索出数据后,可以使用链式调用数据操作API来对数据进行操作

# =============================================== 检索数据 ======================================== #
------------------------
唯一结果检索	get()	|
------------------------
	Model.objects.get()
		* 根据参数仅仅检索出一条数据
		* 参数为关键字参数,表示条件
			get(id=5,name='KevinBlandy')
		* 如果参数非唯一,或者数据不存在都会抛出异常
	

------------------------
检索出所有		all()	|
------------------------
	Model.objects.all()
		* 直接会检索出所有的记录
		* 可以使用 [index],下标来访问元素
	
	* all api 并不会去执行SQL语句,而是在对 all 结果进行操作的时候,才会去执行SQL语句
	
------------------------
根据条件进行检索 filter()|
------------------------
	Model.objects.filter()
		* 根据参数检索出一条或者多条数据
		* 参数为关键字参数,表示过滤条件
			Model.objects.filter(name='KevinBlandy',num=8575532)
		* 返回的是一个 QuerySet 对象
			* 可以使用 [index],下标来访问元素,支持切片
			* 也可以通过 iterator() 来获取 QuerySet 的迭代器对象
	
	Model.objects.exclude()
		* 同上,结果相反
		* exclude() 参数中表示的条件,不会被检索出来
	
	* filter api 并不会去执行SQL语句,而是在对 filter 结果进行操作的时候,才会去执行SQL语句

	* 在 
	

--------------------------
filter 中复杂的条件表达式 |
--------------------------
	
	


# =============================================== 数据操作 ======================================== #
-----------------------------
仅仅检索指定的字段	values() |
-----------------------------
	Models.objects.filter().values()
		* values(),参数是一个字符串类的序列,指定了需要取出哪些字段
			Models.objects.filter().values("id","name")
		* 返回的是是特殊的 QuerySet,它是一个可迭代的字典序列
		* 结果集合中的每个元素就是一个 dict ,key是DB字段名称,value就是值
	
	Models.objects.filter().values_list()
		* 同上,不过该API检索的结果集元素是元组
		* [('db字段','值')]


--------------------------
对结果进行排序	order_by()|
--------------------------
	Models.objects.filter().order_by()
		* 通过 order_by() 传递的参数对结果集进行排序操作
		* 参数比较有意思,是通过字符串来指定排序字段,以及排序策略
			Models.objects.filter().order_by("age","name")
				* 根据 age 和 name 字段进行正序排序
			Models.objects.filter().order_by("-age","-name")
				* 根据 age 和 name 字段进行逆序排序
				* 没错,如果是要逆序排序,则在字段名称前面加上'-'号
	
	
	Models.objects.filter().order_by().reverse()
		* 通过 reverse() 对结果集进行逆序排序


------------------------
去除重复元素 distinct()	|
------------------------
	Models.objects.filter().distinct()
		* 去除结果集中重复的元素,并且返回
		* 返回的是一个集合

------------------------
记录数量	count()		|
------------------------
	Models.objects.filter().count()
		* 获取符合结果的记录数量

------------------------
获取头尾数据	first()	|
------------------------
	Models.objects.filter().first()
		* 获取结果集中的第一条数据
	Models.objects.filter().last()
		* 获取结果集中的最后一条数据

------------------------
判断是否存在	exists()|
------------------------
	Models.objects.filter().exists()
		* 如果 exists() 参数在结果集中,则返回 True,反之返回 False
	
	


	

	
