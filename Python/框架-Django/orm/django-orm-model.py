----------------------------
django-model				|
----------------------------
	* 参考URL
		http://www.jianshu.com/p/c10be59aad7a

	* Django 对各种数据库提供了很好的支持
		PostgreSQL
		MySQL
		SQLite
		Oracle。
	* Django 为这些数据库提供了统一的调用API,可以根据自己业务需求选择不同的数据库
	* Django规定,如果要使用模型,必须要创建一个app(python manage.py startapp [app])


----------------------------
django-model的创建			|
----------------------------
	* 在django的 settings.py 脚本的 INSTALLED_APPS 集合中,以字符串形式添加当前app的名称
	* 在app目录下创建 models.py
	* 在models.py脚本中创建model实例对象
	* 通过命令生成数据库
		python manage.py makemigrations
			* 生成同步数据库的脚本
			* 需要安装:pip install mysqlclient
	
		python manage.py migrate
			* 同步数据库
	
----------------------------
django-model实例简单定义	|
----------------------------
	from django.db import models

	models.CharField()

	class User(models.Model):	# 必须继承models.Model
		# varchar
		name=models.CharField(max_length=15)
		# int,允许为空
		age=models.IntegerField(max_length=100,null=True)
	
	* 需要注意Model的属性不要使用与API相冲突的字段名称如clean,save或delete
	

----------------------------
django - FieldTypes			|
----------------------------
	* 字段
	* 他们都继承自:django.db.models.fields.Field
	* 可以自定义类去覆写这些字段信息
	CharField
	IntegerField
	BooleanField
		* tinyint(1)
	AutoField
	DateField

------------------------------
django-	FieldType Options	  |
------------------------------
	* 字段通用的属性
	null
		* 如果为 True ,Django 在数据库中会将空值(empty)存储为 NULL ,默认为 False 

	blank
		* 该字段是否可以为空,默认为 False (不允许为空)
		* 属性null是数据库的范围,而blank是用于验证
		* 如果一个字段的 blank=True ,Django 在进行表单数据验证时,会允许该字段是空值,如果字段的 blank=False ,该字段就是必填的
	
	choices
		* 它是一个可迭代的二元组(例如,列表或是元组)
		* 用来给字段提供选择项,如果设置了 choices, Django会显示选择框,而不是标准的文本框
		* 而且这个选择框的选项就是 choices 中的元组
		* demo
			SHIRT_SIZES = (
				('S', 'Small'),
				('M', 'Medium'),
				('L', 'Large'),
			)
			shirt_size = models.CharField(max_length=1, choices=SHIRT_SIZES)
			p = Person(name="Fred Flintstone", shirt_size="L")
			p.save()

			p.shirt_size				# L
			p.get_shirt_size_display()	# Large

	primary_key
		* 该字段是否为pk,如果该属性为 True,则系统不会默认的创建id字段,默认为 False
		* 默认会创建:id = models.AutoField(primary_key=True)
	
	db_index
		* 该字段是否建立索引,默认为 False
	
	default
		* 默认值,可以是一个具体的值也可以是一个对象,每次调用次会创建一个新的对象
	
	help_text
		* 附加的帮助信息,在使用表单时会显示在字段下面
		* 即使不使用表单也可以起来帮助文档的作用
	
	unique
		* 如果为 True ,那么字段值就必须是全表唯一的
	
	verbose field names
		* 详细名称
		* 每个字段的类型,除了ForeignKey, ManyToManyField 和 OneToOneField外都具备(这仨有专门的关键字属性来控制:verbose_name)
		* 位于第一位置参数,这个参数用于标记字段的详细名称
		* 如果Verbose field names没有显示的给出,Django会自动创建这一字段属性将字段名中的"_"转换成空格
			first_name = models.CharField("person's first name", max_length=30)	--> person's first name
			first_name = models.CharField(max_length=30)						--> first name

	


------------------------------
django-	db元信息:Meta			|
------------------------------
	* 可以自己控制联合主键/表名称等信息
	* 直接在Model里面定义另一个 class:Meta
		class UserRole(models.Model):
			user=models.ForeignKey("User")
			role=models.ForeignKey("Role")

			class Meta:
				db_table = 'root_role_user'         # 表名称
				unique_together = ("role", "user")  # 指定哪个两个属性是唯一约束

	* 也可以继承指定的元类
			class Meta(BaseModel.Meta):
				db_table = 'root_role_user'         
				unique_together = ("role", "user") 
		
		* 子model不能访问到父model的Meta,然而也有少数的情况下,子model会从父model中继承一些行为
		* 例如子model没有指定 ordering 或 get_latest_by属性,那么就会从父model中继承
	
	* Meta类属性
		ordering
			* 排序

		db_table
			* 字符串类型,手动的指定数据表的名称

		unique_together
			* 创建联合唯一索引,该属性值应该是一个序列
		
		abstract
			* 默认为 False,表示当前类是否是抽象的
			* 如果是抽象的则不会创建表,该类的作用仅仅是抽取出了公共的字段,用于子类继承
		
		proxy 
			* 当前类,是否是代理类,代理model必须继承一个非抽像model
			* 例如,想为Person model添加一个方法：
				from django.db import models
				class Person(models.Model):
					first_name = models.CharField(max_length=30)
					last_name = models.CharField(max_length=30)

				class MyPerson(Person):
					class Meta:
						proxy = True

					def do_something(self):
						# ...
						pass
				* MyPerson这个类将作用于父类Person所对应的真实数据表,可通过MyPerson进行所有相应的操作
				* MyPerson来查询Person对象时,返回的QuerySet依然会是Person对象类型的集合
				* 使用代理模式的model是依靠原始model的,是原始model的扩展,而不是用来替代父model

			* 也可以使用代理模式来定义model的不同默认排序
				class OrderedPerson(Person):
					class Meta:
						ordering = ["last_name"]# 使用原始model查询时结果是无序的,而使用OrderedPerson进行查询时将按last_name进行排序
						proxy = True
		
			
------------------------------
django - 多对一 ForeignKey		|
------------------------------



------------------------------
django - 多对多 ManyToManyField	|
------------------------------


------------------------------
django - 一对一 ManyToManyField	|
------------------------------

------------------------------
django - Model属性				|
------------------------------
	objects
		* 这是model最重要的Manager属性,通过它查询数据库并于model之间形成映射
		* 如果没有自定义manager,默认名称为objects
		* managers只能通过model类,而不能通过model类实例来访问
		* 属性方法
			create()
				* 创建一条记录,参数为dict,表示db的字段 & 值

------------------------------
django - Model方法				|
------------------------------
	* model实例继承自models.Model,会自动具备大量的方法
	* 可以对它们进行覆写
	save()
		* 保存
		* 覆写的时候,需要特别注意的要记得调用父类的方法--super(Blog, self).save(args, *kwargs)
		* 以确保对象仍然被保存到数据库中,如果你忘记调用父类的方法,默认的行为不会发生,数据库也不会发生改变

	delete()
		* 删除