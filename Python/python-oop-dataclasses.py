-----------------------------
dataclasses					 |
-----------------------------
	* 3.7的新模块

------------------------------------------
dataclasses.dataclass                     |
------------------------------------------
    # 自动生成属性
	# 模块函数
		dataclass(_cls=None, *, init=True, repr=True, eq=True, order=False,unsafe_hash=False, frozen=False)

		- init 生成__init__方法
		- repr  生成__repr__方法
			* 生成的repr字符串将具有类名以及每个字段的名称和repr,按照它们在类中定义的顺序
			* 标记为从repr中排除的字段不包括在内,例如: InventoryItem(name ='widget', unit_price = 3.0,quantity_on_hand = 10)
			* 如果类已定义__repr__,则忽略此参数
		- eq 生成__eq__方法
			* 此方法按顺序比较类,就好像它是字段的元组一样,比较中的两个实例必须是相同的类型
			* 如果类已定义__eq__,则忽略此参数
		- order 生成__lt __,__ le __, __ gt__和__ge__方法
			* 些按顺序将类比较为它的字段元组,比较中的两个实例必须是相同的类型
			* 如果 order为true且eq为false.则引发ValueError。
			* 如果类已经定义任何的__lt__,__le__,__gt__,或__ge__,然后ValueError异常升高(???)

		- unsafe_hash
            * 如果是False, 将根据eq和frozen参数来生成__hash__:
                1. eq和frozen都为True, __hash__将会生成
                2. eq为True而frozen为False, __hash__被设为None
                3. eq为False, frozen为True, __hash__将使用超类(object)的同名属性(通常就是基于对象id的hash)
            
            * 当设置为True时将会根据类属性自动生成__hash__, 然而这是不安全的
            * 因为这些属性是默认可变的, 这会导致hash的不一致, 所以除非能保证对象属性不可随意改变, 否则应该谨慎地设置该参数为True
            
		- frozen
            * 设为True时对field赋值将会引发错误, 对象将是不可变的
            * 如果已经定义了__setattr__和__delattr__将会引发TypeError

	# demo
		import dataclasses

		@dataclasses.dataclass
		class User():
			# 声明属性名称以及属性的类型
			id: int
			name: str
			# 默认值
			join: bool = True
			
			def __str__(self):
				return 'id=%d,name=%s,join=%s' % (self.id, self.name, self.join)

		print(User(1, 'KevinBlandy'))  # id=1,name=KevinBlandy,join=True
		print(User(1, 'KevinBlandy', False))  # id=1,name=KevinBlandy,join=False
 
------------------------------------------
dataclasses 其他模块方法                  |
------------------------------------------
    dataclasses.field 
        * 方法原型
            dataclasses.field(*, default=MISSING, default_factory=MISSING, repr=True, hash=None, init=True, compare=True, metadata=None)
        
     dataclasses.asdict
     dataclasses.astuple


























