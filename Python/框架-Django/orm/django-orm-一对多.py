---------------------------
一对多-Model定义			|
---------------------------
	# 国别
	class Country(models.Model):
		name = models.CharField(max_length=15)


	# 用户
	class User(models.Model):
		name=models.CharField(max_length=15)
		country=models.ForeignKey("Country")          # 一对多关系,一个用户只能属于一个国家,一个国家可以有多个用户(ForeignKey,写在多的一方)
		#roles=models.ManyToManyField("Role")          # 多对多关系,一个用户可以有多个角色,一个角色也可以有多个用户


	# 角色
	class Role(models.Model):
		name=models.CharField(max_length=15)
		users=models.ManyToManyField("User")          # 多对多关系,一个角色可以有多个用户,一个用户也可以有多个角色

	# 身份证
	class Card(models.Model):
		num=models.IntegerField(null=False)
		user=models.OneToOneField("User")             # 一对一关系,一个卡片只能对应一个用户,一个用户只能有一个卡片(OneToOneField,写在哪方,外键就在哪方)

---------------------------
一对多-ForeignKey			|
---------------------------
	models.ForeignKey(to, on_delete=None, related_name=None, related_query_name=None,
						 limit_choices_to=None, parent_link=False, to_field=None,
						 db_constraint=True, **kwargs)
		* 建立一对多关系,该字段属于多的一方
		* 参数详解
			to
				* '必须参数'
				* 一的一方Class类对象
				* django会默认的给该字段加上下划线id(country=models.ForeignKey(Country) ==> country_id)


		* 关键字参数详解
			on_delete
			related_name
			related_query_name
			limit_choices_to
			parent_link
			to_field
				* 指定当前字段(外键),是多方表中的哪个字段(索引),默认为pk

			db_constraint


---------------------------
一对多-create				|
---------------------------
	* 很显然,在插入时候需要注意的仅仅是多的一方
	1,直接插入ID值
		Model.objects.create()
			* 参数为db的字段名称 & value
			* Model.objects.create(name='KevinBlandy',age=23,country_id=1)
	
		model = Model('KevinBlandy',23,1)
		model.save()
			* 也可以直接初始化的时候,对外键字段进行值的初始化
			* 创建model实例对象,直接调用实例的save()方法
		
		* 外键字段,直接设置为一的一方的ID值即可,注意,这里的外键的关键字参数名称'必须是db的字段名,不是对象的属性名'(country_id)
	
	2,oop的方式
		Model.objects.create()
			* 参数为实例属性的键值对
			* Model.objects.create(name='KevinBlandy',age=23,country=Country(1,'China'))
	
		model = Model('KevinBlandy',23,Country(1,'China'))
		model.save()
			* 也可以直接初始化的时候,对外键字段进行值的初始化
			* 创建model实例对象,直接调用实例的save()方法
		
		* 实例对象必须具备ID值
		* 外键字段,设置为多的一方的对象实例,注意,这里的外键的关键字参数名称'是对象的正常属性名,非db字段名称'(Country)
			