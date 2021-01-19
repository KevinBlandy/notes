-----------------------
枚举					|
-----------------------
	* 枚举类的实例个数是固定的,且他们都是单例
	* 出现重复名称的实例会抛出异常
	* 需要使用到 enum 模块的 Enum 类

-----------------------
简单的枚举				|
-----------------------
	* 简单的枚举
		from enum import Enum

		Month = Enum('Month', ('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'))
		
		* 这样我们就获得了 Month 类型的枚举类
			直接引用一个常量
				Month.Jan
				Month['Jan']		# Weekday.Sun			字典访问法
				Month(1)			# Weekday.Sun			根据值获取法
				type(Month.Jan)				# <enum 'Month'>
				Month.Mar == Month.Mar		# True

			或者枚举它的所有成员
				for name, member in Month.__members__.items():
					print(name, '=>', member, ',', member.value)
					# Jan => Month.Jan , 1
					# .. ...
		
		* 枚举类的 __members__ 属性,返回当前枚举类的所有 实例名 & 实例 的字典(排序)映射
			OrderedDict([('Jan', <Month.Jan: 1>), ('Feb', <Month.Feb: 2>), ('Mar', <Month.Mar: 3>), ('Apr', <Month.Apr: 4>), ('May', <Month.May: 5>), ('Jun', <Month.Jun: 6>), ('Jul', <Month.Jul: 7>), ('Aug', <Month.Aug: 8>), ('Sep', <Month.Sep: 9>), ('Oct', <Month.Oct: 10>), ('Nov', <Month.Nov: 11>), ('Dec', <Month.Dec: 12>)])
		
		* 枚举类实例的 value 属性则是自动赋给枚举成员的int常量,默认从1开始计数(反人类啊),且只读
	
-----------------------
继承的枚举				|
-----------------------
	from enum import Enum, unique

	@unique
	class Weekday(Enum):	# 继承枚举类
		Sun = 0				# Sun的value被设定为0
		Mon = 1
		Tue = 2
		Wed = 3
		Thu = 4
		Fri = 5
		Sat = 6

	* @unique装饰器可以帮助我们检查保证没有重复值,注意,它检查的是枚举实例的值,是否重复,并不是枚举的名称是否重复
	* 枚举实例的值也不允许重复,不一定是 int,可以是任意数据类型
	* 访问单个枚举
		Weekday.Sun			# Weekday.Sun			常规访问法
		Weekday['Sun']		# Weekday.Sun			字典访问法
		Weekday(0)			# Weekday.Sun			根据值获取法
		type(Weekday.Sun)	# <enum 'Weekday'>
		Weekday.Sun.value	# 0
	
	* 遍历枚举
		for name,item in Weekday.__members__.items():
			print('枚举名称:%s,枚举实例对象:%s,枚举值:%s'%(name,item,item.value))
		# 枚举名称:Sun,枚举实例对象:Weekday.Sun,枚举值:0
		# 枚举名称:Mon,枚举实例对象:Weekday.Mon,枚举值:1
		# 枚举名称:Tue,枚举实例对象:Weekday.Tue,枚举值:2
		# 枚举名称:Wed,枚举实例对象:Weekday.Wed,枚举值:3
		# 枚举名称:Thu,枚举实例对象:Weekday.Thu,枚举值:4
		# 枚举名称:Fri,枚举实例对象:Weekday.Fri,枚举值:5
		# 枚举名称:Sat,枚举实例对象:Weekday.Sat,枚举值:6
				
	* 枚举类的 __members__ 属性,返回当前枚举类的所有 实例名 & 实例 的字典(排序)映射
			OrderedDict([('Jan', <Month.Jan: 1>), ('Feb', <Month.Feb: 2>), ('Mar', <Month.Mar: 3>), ('Apr', <Month.Apr: 4>), ('May', <Month.May: 5>), ('Jun', <Month.Jun: 6>), ('Jul', <Month.Jul: 7>), ('Aug', <Month.Aug: 8>), ('Sep', <Month.Sep: 9>), ('Oct', <Month.Oct: 10>), ('Nov', <Month.Nov: 11>), ('Dec', <Month.Dec: 12>)])
	
	* 访问枚举实例的值,使用实例的 value 属性,且该值是只读属性
		


