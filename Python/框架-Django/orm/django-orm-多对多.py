---------------------------
多对多-Model定义			|
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
多对多-ManyToManyField		|
---------------------------
	* 如果双方都声明了多对多的字段那么就会生成2张中间表,贼TM吓人(用户对象具备字段:roles=models.ManyToManyField("Role"))
		`root_user_roles`	# 用户关联角色
			`id` 
			`user_id`
			`role_id` 
		
		`root_role_users`	# 角色关联用户
			`id` 
			`role_id`
			`user_id`

	* 中间表会有自己的ID字段,多对多字段声明在哪个对象,则表名称就会以该对象名称优先
		`root_role_users`	# 角色关联用户
			`id` 
			`role_id`
			`user_id` 

	ManyToManyField(to, related_name=None, related_query_name=None,
					 limit_choices_to=None, symmetrical=None, through=None,
					 through_fields=None, db_constraint=True, db_table=None,
					 swappable=True, **kwargs)
		* 多对多关系,该字段会存在于双方
		* 参数详解
			to
				* '必须参数'
				* 多对多的另一方
				* django会在中间表中添加对应的ID字段
		
		* 关键字参数详解
			related_name
			related_query_name
			limit_choices_to
			symmetrical
				* 当使用中间模型来定义一个到自己的多对多关系的模型时,你必须使用symmetrical=False

			through
				* 不想通过django来创建中间表,那么可以通过该属性来指向一个'中间对象'
				* 该中间对象就是具备了两个一对多关系的对象

			through_fields
				* 如果中间表有两个或以上的外键定义
				* 必须要定义through_fields,否则会产生校验错误
				
			db_constraint
			db_table
				* 可以手动的指定中间表的名称

			swappable


---------------------------
多对多-create				|
---------------------------
	* 对双方都是一样的
	* 双方多对多的属性值应该是一个序列,因为可能会有多个
	* 多对多中的插入,必须要使用 oop 的方式
	* 实例对象必须具备ID值
	
		role = Role(1)
		role.users.add(User(1,'KevinBlandy'))
		#role.users.remove(User(1,'KevinBlandy'))	移除
		role.save()
		
		* 多对多的外键字段属性,本质上就是一个集合,直接可以用集合的api进行操作

	
	* 正向与反向
		* 当中间表是交给了 django 管理的时候,会出现该情况
			
		# 用户
		class User(models.Model):
			name=models.CharField(max_length=15)

		# 角色
		class Role(models.Model):
			name=models.CharField(max_length=15)
			users=models.ManyToManyField("User")          # 多对多关系,一个角色可以有多个用户,一个用户也可以有多个角色

		# 正向--------------------------------------(所谓的正向,其实就是从 ManyToManyField 所在的对象去关联另一堆对象)
			# 获取id=1的角色信息
			role = Role.objects.filter(id=1)[0]
			# 获取id > 1的所有用户信息
			users = User.objects.filter(id__gt=1)
			# 把所有用户添加到角色中的用户列表
			role.users.add(*users)
			role.save()

		# 逆向-------------------------------------(所谓的逆向,其实就是在没有 ManyToManyField 的对象中添加了一个隐藏的关联属性,可以通过该属性来操作多对多关联的数据 )
			# 获取id=1的用户信息
			user = User.objects.filter(id=1)[0]
			# 获取id > 1的所有角色信息
			roles = Role.objects.filter(id__gt=1)
			# 把所有的角色,添加到用户中的角色列表
			'''
				User没有 role 这个属性,是因为多对多的关系,自动生成的
				根据多对多的另一方类名称(Role),小写加 _set
			'''
			user.role_set.add(*roles)
			user.save()
---------------------------
多对多-delete				|
---------------------------
	* 多对多中的删除,默认是级联删除
		User.objects.filter(id=1).delete()
		* 删除id=1的用户,会级联的删除掉多对多关联表中所有user_i=1的记录
	

	



---------------------------
多对多-手动创建多对多		|
---------------------------
	* 其实并不神奇,我们只是通过一个中间的关联对象(俩一对多属性),来完成了多对多的映射
		# 用户
		class User(models.Model):
			name=models.CharField(max_length=15)

		# 角色
		class Role(models.Model):
			name=models.CharField(max_length=15)
		
		# 关联对象
		class UserRole(models.Model):
			user=models.ForeignKey("User")		# 一对多的关联用户
			role=models.ForeignKey("Role")		# 一对多的关联角色
			
	
	* 这种方式,就可以使用非OOP的方式来保存/修改多对多的映射关系(参阅一对多笔记)
	* 这种方式不用声明ManyToManyField字段
	* 如果你非要通过声明ManyToManyField字段的方式来实现,那么该字段有一个属性值(through),可以设置中间表对象
		class Role(models.Model):
			name=models.CharField(max_length=15)
			users=models.ManyToManyField("User",through="UserRole") 
	
