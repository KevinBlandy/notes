---------------------------
一对一-Model定义			|
---------------------------
	# 国别
	class Country(models.Model):
		name = models.CharField(max_length=15)


	# 用户
	class User(models.Model):
		name=models.CharField(max_length=15)
		country=models.ForeignKey("Country")          # 一对多关系,一个用户只能属于一个国家,一个国家可以有多个用户(ForeignKey,写在多的一方)
		#roles=models.ManyToManyField("Role")         # 多对多关系,一个用户可以有多个角色,一个角色也可以有多个用户


	# 角色
	class Role(models.Model):
		name=models.CharField(max_length=15)
		users=models.ManyToManyField("User")          # 多对多关系,一个角色可以有多个用户,一个用户也可以有多个角色

	# 身份证
	class Card(models.Model):
		num=models.IntegerField(null=False)
		user=models.OneToOneField("User")             # 一对一关系,一个卡片只能对应一个用户,一个用户只能有一个卡片(OneToOneField,写在哪方,外键就在哪方)


---------------------------
一对一-OneToOneField		|
---------------------------
	OneToOneField(to, on_delete=None, to_field=None, **kwargs)
		* 创建一对一的关系
		* OneToOneField 在哪一方,外键就在哪一方
		* 参数
			to
				* 一对一的目标对象

		* 关键字参数
			on_delete
			to_field

		